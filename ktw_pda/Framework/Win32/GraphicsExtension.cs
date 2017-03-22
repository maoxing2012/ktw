//--------------------------------------------------------------------- 
//THIS CODE AND INFORMATION ARE PROVIDED AS IS WITHOUT WARRANTY OF ANY 
//KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
//IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A 
//PARTICULAR PURPOSE. 
//---------------------------------------------------------------------
using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Drawing.Imaging;
using PlatformAPI;

namespace Microsoft.Drawing
{
    public static class GraphicsExtension
    {

        /// <summary>
        /// Draws an image with transparency
        /// </summary>
        /// <param name="gx">Graphics to drawn on.</param>
        /// <param name="image">Image to draw.</param>
        /// <param name="transparency">Transparency constant</param>
        /// <param name="x">X location</param>
        /// <param name="y">Y location</param>
        public static void DrawAlpha(this Graphics gx, Bitmap image, byte transparency, int x, int y)
        {
            using (Graphics gxSrc = Graphics.FromImage(image))
            {
                IntPtr hdcDst = gx.GetHdc();
                IntPtr hdcSrc = gxSrc.GetHdc();
                BlendFunction blendFunction = new BlendFunction();
                blendFunction.BlendOp = (byte)BlendOperation.AC_SRC_OVER;   // Only supported blend operation
                blendFunction.BlendFlags = (byte)BlendFlags.Zero;           // Documentation says put 0 here
                blendFunction.SourceConstantAlpha = transparency;           // Constant alpha factor
                blendFunction.AlphaFormat = (byte)0;                        // Don't look for per pixel alpha
                PlatformAPIs.AlphaBlend(hdcDst, x, y, image.Width, image.Height, 
                               hdcSrc, 0, 0, image.Width, image.Height, blendFunction);
                gx.ReleaseHdc(hdcDst);                                      // Required cleanup to GetHdc()
                gxSrc.ReleaseHdc(hdcSrc);                                   // Required cleanup to GetHdc()
            }
        }


        public static void DrawImageAlphaChannel(this Graphics gx, IImage image, int x, int y)
        {
            ImageInfo imageInfo = new ImageInfo();
            image.GetImageInfo(out imageInfo);
            Rectangle rc = new Rectangle(x, y, (int)imageInfo.Width + x, (int)imageInfo.Height + y);
            IntPtr hdc = gx.GetHdc();
            image.Draw(hdc, ref rc, IntPtr.Zero);
            gx.ReleaseHdc(hdc);
        }

        public static void DrawImageAlphaChannel(this Graphics gx, IImage image, Rectangle dest)
        {           
            Rectangle rc = new Rectangle(dest.X, dest.Y, dest.Width + dest.X, dest.Height + dest.Y);
            IntPtr hdc = gx.GetHdc();
            image.Draw(hdc, ref rc, IntPtr.Zero);
            gx.ReleaseHdc(hdc);
        }

        /// <summary>
        /// Draws gradient filled rounded rectangle with transparency
        /// </summary>
        /// <param name="gx">Destination graphics</param>
        /// <param name="rc">Destination rectangle</param>
        /// <param name="startColorValue">Starting color for gradient</param>
        /// <param name="endColorValue">End color for gradient</param>
        /// <param name="borderColor">The color of the border</param>
        /// <param name="size">The size of the rounded corners</param>
        /// <param name="transparency">Transparency constant</param>
        public static void DrawGradientRoundedRectangleAlpha(this Graphics gx, Rectangle rc, Color startColorValue, Color endColorValue, Color borderColor, Size size, byte transparency)
        {

            // Prepare image for gradient
            Bitmap gradientImage = new Bitmap(rc.Width, rc.Height);
            // Create temporary graphics
            Graphics gxGradient = Graphics.FromImage(gradientImage);
            // This is our rectangle
            Rectangle roundedRect = new Rectangle(0, 0, rc.Width, rc.Height);
            // Fill in gradient
            GradientFill.Fill(
                gxGradient,
                roundedRect,
                startColorValue,
                endColorValue,
                FillDirection.TopToBottom);           
          
            // Prepare the copy of the screen graphics
            Bitmap tempBitmap = new Bitmap(240, 320);
            Graphics tempGx = Graphics.FromImage(tempBitmap);
            // Copy from the screen's graphics to the temp graphics
            CopyGraphics(gx, tempGx, 240, 320);                
            // Draw the gradient image with transparency on the temp graphics
            tempGx.DrawAlpha(gradientImage, transparency, rc.X, rc.Y);
            // Cut out the transparent image 
            gxGradient.DrawImage(tempBitmap, new Rectangle(0, 0, rc.Width, rc.Height), rc, GraphicsUnit.Pixel);
            // Prepare for imposing
            roundedRect.Width--;
            roundedRect.Height--;
            // Impose the rounded rectangle with transparent color
            Bitmap borderImage = ImposeRoundedRectangle(roundedRect, borderColor, size);
            // Draw the transparent rounded rectangle
            ImageAttributes attrib = new ImageAttributes();
            attrib.SetColorKey(Color.Green, Color.Green);
            gxGradient.DrawImage(borderImage, new Rectangle(0, 0, rc.Width, rc.Height), 0, 0, borderImage.Width, borderImage.Height, GraphicsUnit.Pixel, attrib);                      
            // OK... now are ready to draw the final image on the original graphics
            gx.DrawImageTransparent(gradientImage, rc); 
         
            // Clean up
            attrib.Dispose();
            tempGx.Dispose();
            tempBitmap.Dispose();
            gradientImage.Dispose();
            gxGradient.Dispose();            
        }

        private static void CopyGraphics(Graphics gxSrc, Graphics gxDest, int width, int height)
        {
            IntPtr destDc = gxDest.GetHdc();
            IntPtr srcDc = gxSrc.GetHdc();
            PlatformAPIs.BitBlt(destDc, 0, 0, width, height, srcDc, 0, 0, TernaryRasterOperations.SRCCOPY);
            gxSrc.ReleaseHdc(srcDc);
            gxDest.ReleaseHdc(destDc);          
        }
        
        /// <summary>
        /// Draws the image with transparency
        /// </summary>
        /// <param name="gx">Destination graphics</param>
        /// <param name="image">The image to draw</param>
        /// <param name="destRect">Desctination rectangle</param>
        public static void DrawImageTransparent(this Graphics gx, Image image, Rectangle destRect)
        {
            ImageAttributes imageAttr = new ImageAttributes();
            Color transpColor = GetTransparentColor(image);
            imageAttr.SetColorKey(transpColor, transpColor);
            gx.DrawImage(image, destRect, 0, 0, image.Width, image.Height, GraphicsUnit.Pixel, imageAttr);
            imageAttr.Dispose();
        }
        
        /// <summary>
        /// Returns the rectangle filled with gradient colors
        /// </summary>
        /// <param name="rc">Destination rectangle</param>
        /// <param name="startColorValue">Starting color for gradient</param>
        /// <param name="endColorValue">End color for gradient</param>
        /// <param name="fillDirection">The direction of the gradient</param>
        /// <returns>Image of the rectanle</returns>
        public static Bitmap GetGradientRectangle(Rectangle rc, Color startColorValue, Color endColorValue, FillDirection fillDirection)
        {
             Bitmap outputImage = new Bitmap(rc.Width, rc.Height);
             // Create temporary graphics
             Graphics gx = Graphics.FromImage(outputImage);

              GradientFill.Fill(
                gx,
                rc,
                startColorValue,
                endColorValue,
                fillDirection);

              return outputImage;
        }

        /// <summary>
        /// Fills the rectagle with gradient colors
        /// </summary>
        /// <param name="gx">Destination graphics</param>
        /// <param name="rc">Desctination rectangle</param>
        /// <param name="startColorValue">Starting color for gradient</param>
        /// <param name="endColorValue">End color for gradient</param>
        /// <param name="fillDirection">The direction of the gradient</param>
        public static void FillGradientRectangle(this Graphics gx, Rectangle rc, Color startColorValue, Color endColorValue, FillDirection fillDirection)
        {          
            GradientFill.Fill(
              gx,
              rc,
              startColorValue,
              endColorValue,
              fillDirection);            
        }
        
        private static Color GetTransparentColor(Image image)
        {
            return ((Bitmap)image).GetPixel(image.Width - 1, image.Height - 1);
        }

        /// <summary>
        /// Draws gradient filled rounded rectangle
        /// </summary>
        /// <param name="gx">Destination graphics</param>
        /// <param name="rc">Desctination rectangle</param>
        /// <param name="startColorValue">Starting color for gradient</param>
        /// <param name="endColorValue">End color for gradient</param>
        /// <param name="borderColor">The color of the border</param>
        /// <param name="size">The size of the rounded corners</param>
        public static void DrawGradientRoundedRectangle(this Graphics gx, Rectangle rc, Color startColorValue, Color endColorValue, Color borderColor, Size size)
        {           
            Bitmap bitmap = GetGradiendRoundedRectangle(new Rectangle(0, 0, rc.Width, rc.Height), startColorValue, endColorValue, borderColor, size);
            gx.DrawImageTransparent(bitmap, rc);                         
        }

        /// <summary>
        /// Returns the image of the gradient filled rounded rectangle
        /// </summary>     
        /// <param name="rc">Desctination rectangle</param>
        /// <param name="startColorValue">Starting color for gradient</param>
        /// <param name="endColorValue">End color for gradient</param>
        /// <param name="borderColor">The color of the border</param>
        /// <param name="size">The size of the rounded corners</param>
        /// <returns>Bitmap image</returns>
        public static Bitmap GetGradiendRoundedRectangle(Rectangle rc, Color startColorValue, Color endColorValue,  Color borderColor, Size size)
        {
            Bitmap outputImage = new Bitmap(rc.Width, rc.Height);
            // Create temporary graphics
            Graphics gx = Graphics.FromImage(outputImage);

            GradientFill.Fill(
                gx,
                rc,
                startColorValue,
                endColorValue,
                FillDirection.TopToBottom);

            Rectangle roundedRect = rc;
            roundedRect.Width--;
            roundedRect.Height--;

            Bitmap borderImage = ImposeRoundedRectangle(roundedRect, borderColor, size);
           
            ImageAttributes attrib = new ImageAttributes();
            attrib.SetColorKey(Color.Green, Color.Green);

            gx.DrawImage(borderImage, rc, 0, 0, borderImage.Width, borderImage.Height, GraphicsUnit.Pixel, attrib); 
           
            // Clean up
            attrib.Dispose();
            gx.Dispose();

            return outputImage;           
        }

        /// <summary>
        /// Returns the image of the gradient filled rounded rectangle
        /// </summary>     
        /// <param name="rc">Desctination rectangle</param>
        /// <param name="startColorValue">Starting color for gradient</param>
        /// <param name="endColorValue">End color for gradient</param>
        /// <param name="borderColor">The color of the border</param>
        /// <param name="size">The size of the rounded corners</param>
        /// <returns>Bitmap image</returns>
        public static Bitmap GradiendRoundedRectangle2(Rectangle rc, Color startColorValue, Color endColorValue, Color borderColor, Size size)
        {
            Bitmap outputImage = new Bitmap(rc.Width, rc.Height);
            Graphics gx = Graphics.FromImage(outputImage);

            Rectangle rectTopMiddle = new Rectangle(0, 0, rc.Width, rc.Height / 2);

            Rectangle rectMiddleBottom = new Rectangle(0, rc.Height / 2, rc.Width, rc.Height / 2);


            GradientFill.Fill(
                gx,
                rectTopMiddle,
                endColorValue,
                startColorValue,                
                FillDirection.TopToBottom);

            GradientFill.Fill(
               gx,
               rectMiddleBottom,               
               startColorValue,
               endColorValue,
               FillDirection.TopToBottom);


            Rectangle roundedRect = rc;
            roundedRect.Width--;
            roundedRect.Height--;

            Bitmap borderImage = ImposeRoundedRectangle(roundedRect, Color.Green, size);

            ImageAttributes attrib = new ImageAttributes();
            attrib.SetColorKey(Color.Green, Color.Green);
            gx.DrawImage(borderImage, rc, 0, 0, borderImage.Width, borderImage.Height, GraphicsUnit.Pixel, attrib);

            attrib.Dispose();
            gx.Dispose();

            return outputImage;
        }

        private static Bitmap ImposeRoundedRectangle(Rectangle rc, Color borderColor, Size size)
        {          

            Bitmap bitmap = new Bitmap(rc.Width + 1, rc.Height + 1);
            //Create temp graphics
            Graphics gxTemp = Graphics.FromImage(bitmap);
            gxTemp.Clear(Color.White);
            DrawRoundedRectangle(gxTemp, borderColor, Color.Green, rc, size);
            gxTemp.Dispose();
            return bitmap;
        }

        /// <summary>
        /// Draws rounded rectangle
        /// </summary>
        /// <param name="gx">Destination graphics</param>
        /// <param name="pen">The pen to draw</param>
        /// <param name="backColor"></param>
        /// <param name="rc"></param>
        /// <param name="size"></param>
        public static void DrawRoundedRectangle(this Graphics gx, Color borderColor, Color backColor, Rectangle rc, Size size)
        {
            Pen borderPen = new Pen(borderColor);
            DrawRoundedRect(gx, borderPen, backColor, rc, size);
            borderPen.Dispose();
        }

        public static void DrawRectandleAlpha(this Graphics gx, Color borderColor, Color backColor, Rectangle rc, byte transparency)
        {
            Bitmap tempBitmap = new Bitmap(rc.Width, rc.Height);
            Graphics tempGraphics = Graphics.FromImage(tempBitmap);
            using(Brush backColorBrush = new SolidBrush(backColor))
            {
                tempGraphics.FillRectangle(backColorBrush, new Rectangle(0, 0, rc.Width, rc.Height));
            }

            using (Pen borderPen = new Pen(borderColor))
            {
                tempGraphics.DrawRectangle(borderPen, new Rectangle(0, 0, rc.Width, rc.Height));
            }

            gx.DrawAlpha(tempBitmap, transparency, rc.X, rc.Y);
            tempBitmap.Dispose();
            tempGraphics.Dispose();
            
        }

        public static void DrawRoundedRectangleAlpha(this Graphics gx, Color borderColor, Color backColor, Rectangle rc, Size size, byte transparency)
        {
            Pen borderPen = new Pen(borderColor);
            ////Create temp bitmap
            //Bitmap tempBitmap = new Bitmap(rc.Width, rc.Height);
            //Graphics tempGraphics = Graphics.FromImage(tempBitmap);
            //DrawRoundedRect(tempGraphics, borderPen, backColor, new Rectangle(0, 0, rc.Width, rc.Width), size);
            //gx.DrawAlpha(tempBitmap, transparency, rc.X, rc.Y);

            ////Clean up
            //borderPen.Dispose();
            //tempGraphics.Dispose();
            //tempBitmap.Dispose();

            // Prepare image for gradient
            Bitmap roundedImage = new Bitmap(rc.Width, rc.Height);
            // Create temporary graphics
            Graphics gxRounded = Graphics.FromImage(roundedImage);
            // This is our rectangle
            Rectangle roundedRect = new Rectangle(0, 0, rc.Width, rc.Height);
            // Draw rounded rect
            DrawRoundedRect(gxRounded, borderPen, backColor, new Rectangle(0, 0, rc.Width, rc.Width), size);

            // Prepare the copy of the screen graphics
            Bitmap tempBitmap = new Bitmap(240, 320);
            Graphics tempGx = Graphics.FromImage(tempBitmap);
            // Copy from the screen's graphics to the temp graphics
            CopyGraphics(gx, tempGx, 240, 320);
            // Draw the gradient image with transparency on the temp graphics
            tempGx.DrawAlpha(roundedImage, transparency, rc.X, rc.Y);
            // Cut out the transparent image 
            gxRounded.DrawImage(tempBitmap, new Rectangle(0, 0, rc.Width, rc.Height), rc, GraphicsUnit.Pixel);
            // Prepare for imposing
            roundedRect.Width--;
            roundedRect.Height--;
            // Impose the rounded rectangle with transparent color
            Bitmap borderImage = ImposeRoundedRectangle(roundedRect, borderColor, size);
            // Draw the transparent rounded rectangle
            ImageAttributes attrib = new ImageAttributes();
            attrib.SetColorKey(Color.Green, Color.Green);
            gxRounded.DrawImage(borderImage, new Rectangle(0, 0, rc.Width, rc.Height), 0, 0, borderImage.Width, borderImage.Height, GraphicsUnit.Pixel, attrib);
            // OK... now are ready to draw the final image on the original graphics
            gx.DrawImageTransparent(roundedImage, rc);

            // Clean up
            attrib.Dispose();
            tempGx.Dispose();
            tempBitmap.Dispose();
            roundedImage.Dispose();
            gxRounded.Dispose();            
        }


        public static void DrawRoundedRect(Graphics g, Pen p, Color backColor, Rectangle rc, Size size)
        {
            Point[] points = new Point[8];

            //prepare points for poligon
            points[0].X = rc.Left + size.Width / 2;
            points[0].Y = rc.Top + 1;   
            points[1].X = rc.Right - size.Width / 2;
            points[1].Y = rc.Top + 1;

            points[2].X = rc.Right;
            points[2].Y = rc.Top + size.Height / 2;
            points[3].X = rc.Right;
            points[3].Y = rc.Bottom - size.Height / 2;

            points[4].X = rc.Right - size.Width / 2;
            points[4].Y = rc.Bottom;
            points[5].X = rc.Left + size.Width / 2;
            points[5].Y = rc.Bottom;

            points[6].X = rc.Left + 1;
            points[6].Y = rc.Bottom - size.Height / 2;
            points[7].X = rc.Left + 1;
            points[7].Y = rc.Top + size.Height / 2;

            //prepare brush for background
            Brush fillBrush = new SolidBrush(backColor);

            //draw side lines and circles in the corners
            g.DrawLine(p, rc.Left + size.Width / 2, rc.Top,
             rc.Right - size.Width / 2, rc.Top);
                       
            g.FillEllipse(fillBrush, rc.Right - size.Width, rc.Top,
             size.Width, size.Height);

            g.DrawEllipse(p, rc.Right - size.Width, rc.Top,
            size.Width, size.Height);


            g.DrawLine(p, rc.Right, rc.Top + size.Height / 2,
             rc.Right, rc.Bottom - size.Height / 2);

            g.FillEllipse(fillBrush, rc.Right - size.Width, rc.Bottom - size.Height,
            size.Width, size.Height);

            g.DrawEllipse(p, rc.Right - size.Width, rc.Bottom - size.Height,
             size.Width, size.Height);

           

            g.DrawLine(p, rc.Right - size.Width / 2, rc.Bottom,
             rc.Left + size.Width / 2, rc.Bottom);

            g.FillEllipse(fillBrush, rc.Left, rc.Bottom - size.Height,
            size.Width, size.Height);

            g.DrawEllipse(p, rc.Left, rc.Bottom - size.Height,
             size.Width, size.Height);
           
            

            g.DrawLine(p, rc.Left, rc.Bottom - size.Height / 2,
             rc.Left, rc.Top + size.Height / 2);
            g.FillEllipse(fillBrush, rc.Left, rc.Top,
             size.Width, size.Height);

            g.DrawEllipse(p, rc.Left, rc.Top,
            size.Width, size.Height);
                  
            //fill the background and remove the internal arcs  
            g.FillPolygon(fillBrush, points);
            //dispose the brush
            fillBrush.Dispose();
        } 

        
    }
}
