using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Reflection;

using PlatformAPI;


namespace Framework
{
    /// <summary>
    /// Alpha Image class supporting both alpha channel from image and alpha blending.
    /// The alpha channel is handled through IImage COM API and the alpha blending through AlphaBlend WM5 API.
    /// This class also provides helper creation methods to load an image from a file, a resource or a binary stream.
    /// </summary>
	public class AlphaImage : IDisposable
	{
        // The IImage, for alpha channel.
        private IImage _image;

        //  A buffer used for better performances when performing the alpha blending.
        private Bitmap _buffer;

        private Bitmap _buffer2;

        // The Alpha value.
        private uint _alpha;


        /// <summary>
        /// The IImage instance, handling alpha channel (PNG or GIF file).
        /// </summary>
        public IImage Image
        {
            get { return _image; }
            set
            {
                if (_image == value)
                    return;

                _image = value;

                if (_buffer != null)
                    _buffer.Dispose();
            }
        }

        /// <summary>
        /// 拉升后的图片
        /// </summary>
        public Bitmap LastImage
        {
            get
            {
                return _buffer2;
            }
        }

        public bool CachImage { get; set; }

        /// <summary>
        /// Opacity value used for Alpha Blending.
        /// If no opacity is required, sets this parameter to 0 (default).
        /// </summary>
		public uint Alpha
		{
			get { return _alpha; }
			set { _alpha = value; }
		}


        /// <summary>
        /// Creates a new AlphaImage from the given file name.
        /// <example>
        /// string path = System.IO.Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetName().CodeBase);
        /// AlphaImage image = AlphaImage.CreateFromFile(path + @"\Resources\pix.png");
        /// </example>
        /// </summary>
		public static AlphaImage CreateFromFile(string imageFileName)
		{
			IImagingFactory factory = CreateFactory();
			AlphaImage alphaImage = new AlphaImage();
			factory.CreateImageFromFile(imageFileName, out alphaImage._image);
			return alphaImage;
		}

        /// <summary>
        /// Creates a new AlphaImage from the given resource name.
        /// <example>
        /// AlphaImage image = AlphaImage.CreateFromResource("AlphaMobileControls.TestApp.Resources.Wallpaper.jpg");
        /// </example>
        /// </summary>
		public static AlphaImage CreateFromResource(string imageResourceName)
		{
			MemoryStream stream = 
				(MemoryStream)Assembly.GetCallingAssembly().GetManifestResourceStream(imageResourceName);
			return CreateFromStream(stream);
		}

        /// <summary>
        /// Creates a new AlphaImage from the given memory stream.
        /// </summary>
		public static AlphaImage CreateFromStream(MemoryStream stream)
		{
			IImagingFactory factory = CreateFactory();
			AlphaImage alphaImage = new AlphaImage();
			byte[] pbBuf = stream.GetBuffer();
			uint cbBuf = (uint)stream.Length;
			factory.CreateImageFromBuffer(pbBuf, cbBuf, BufferDisposalFlag.BufferDisposalFlagNone, out alphaImage._image);
			return alphaImage;
		}

		private static IImagingFactory CreateFactory()
		{
			IImagingFactory factory = 
				(IImagingFactory)Activator.CreateInstance(
					Type.GetTypeFromCLSID(new Guid("327ABDA8-072B-11D3-9D7B-0000F81EF32E")));
			return factory;
		}


        #region IDisposable Members

        /// <summary>
        /// Cleaning.
        /// </summary>
        public void Dispose()
        {
            if (_buffer != null)
                _buffer.Dispose();
            _buffer = null;
            _image = null;
            if (_buffer2 != null)
                _buffer2.Dispose();
            _buffer2 = null;
        }

        #endregion

        private readonly static object _lockObject = new object();

        /// <summary>
        /// Draws this image into the given Graphics object.
        /// If the image contains alpha channel, it will used with the buffer.
        /// If an Alpha value is set (> 0), the image will also be alpha blended with the buffer.
        /// </summary>
        /// <param name="gx">The Graphics object to use for drawing.</param>
        /// <param name="bounds">The bounds where to draw.</param>
		public void Draw(Graphics gx, Rectangle bounds)
		{
            lock (_lockObject)
            {
                ImageInfo imgInfo;
                if (_image == null)
                    return;
                _image.GetImageInfo(out imgInfo);

                if (_alpha == 0)
                {
                    //// Creates buffer on demand
                    //if (_buffer == null)
                    //    _buffer = new Bitmap((int)imgInfo.Width, (int)imgInfo.Height, PixelFormat.Format32bppRgb);
                    //Rectangle dstRect = new Rectangle(0, 0, (int)imgInfo.Width, (int)imgInfo.Height);
                    //Rectangle lastRect = new Rectangle(0, 0, bounds.Width, bounds.Height);
                    //using (Graphics gxBuffer = Graphics.FromImage(_buffer))
                    //{
                    //    IntPtr hdcBuffer = gxBuffer.GetHdc();
                    //    IntPtr hdcOrg = gx.GetHdc();

                    //    //按比例缩放背景到缓存图
                    //    if (_buffer2 == null)
                    //        _buffer2 = new Bitmap(bounds.Width, bounds.Height);
                    //    using (Graphics gxBuffer2 = Graphics.FromImage(_buffer2))
                    //    {
                    //        IntPtr hdcBack = gxBuffer2.GetHdc();
                    //        DrawingAPI.BitBlt(hdcBack, 0, 0, (int)bounds.Width, (int)bounds.Height, hdcOrg, bounds.X, bounds.Y, DrawingAPI.SRCCOPY);
                    //        gxBuffer.DrawImage(_buffer2, dstRect, lastRect, GraphicsUnit.Pixel);
                    //        // Draw the image, with alpha channel if any
                    //        _image.Draw(hdcBuffer, ref dstRect, IntPtr.Zero);
                    //        //缩放处理
                    //        gxBuffer2.DrawImage(_buffer, lastRect, dstRect, GraphicsUnit.Pixel);
                    //        gxBuffer2.ReleaseHdc(hdcBack);
                    //    }
                    //    //画图片
                    //    //gx.DrawImage(_buffer, bounds, dstRect, GraphicsUnit.Pixel);
                    //    gx.DrawImage(_buffer2, bounds.X, bounds.Y);
                    //    gx.ReleaseHdc(hdcOrg);
                    //    gxBuffer.ReleaseHdc(hdcBuffer);
                    //}

                    //// Draw the image, with alpha channel if any
                    //IntPtr hdcDest = gx.GetHdc();
                    //Rectangle dstRect = new Rectangle(bounds.X, bounds.Y, (int)imgInfo.Width + bounds.X, (int)imgInfo.Height + bounds.Y);
                    //_image.Draw(hdcDest, ref dstRect, IntPtr.Zero);
                    //gx.ReleaseHdc(hdcDest);

                    // Draw the image, with alpha channel if any
                    IntPtr hdcDest = gx.GetHdc();
                    Rectangle dstRect = new Rectangle(bounds.X, bounds.Y, (int)bounds.Width + bounds.X, (int)bounds.Height + bounds.Y);
                    _image.Draw(hdcDest, ref dstRect, IntPtr.Zero);

                    //缓存图片
                    if (CachImage)
                    {
                        if (_buffer2 == null)
                            _buffer2 = new Bitmap(bounds.Width, bounds.Height);
                        using (Graphics gxBuffer2 = Graphics.FromImage(_buffer2))
                        {
                            IntPtr hdcBack = gxBuffer2.GetHdc();
                            DrawingAPI.BitBlt(hdcBack, 0, 0, (int)bounds.Width, (int)bounds.Height, hdcDest, bounds.X, bounds.Y, DrawingAPI.SRCCOPY);

                            gxBuffer2.ReleaseHdc(hdcBack);
                        }
                    }
                    gx.ReleaseHdc(hdcDest);
                }
                else
                {
                    // Creates buffer on demand
                    if (_buffer == null)
                        _buffer = new Bitmap((int)imgInfo.Width, (int)imgInfo.Height, PixelFormat.Format32bppRgb);

                    using (Graphics gxBuffer = Graphics.FromImage(_buffer))
                    {
                        IntPtr hdcBuffer = gxBuffer.GetHdc();
                        IntPtr hdcOrg = gx.GetHdc();

                        // Copy original DC into Buffer DC to see the background through the image
                        DrawingAPI.BitBlt(hdcBuffer, 0, 0, (int)imgInfo.Width, (int)imgInfo.Height, hdcOrg, bounds.X, bounds.Y, DrawingAPI.SRCCOPY);

                        // Draw the image, with alpha channel if any
                        Rectangle dstRect = new Rectangle(0, 0, (int)imgInfo.Width, (int)imgInfo.Height);
                        Rectangle srcRect = new Rectangle(0, 0, (int)imgInfo.Width, (int)imgInfo.Height);
                        _image.Draw(hdcBuffer, ref dstRect, IntPtr.Zero);
                        //_image.Draw(hdcBuffer, ref dstRect, ref srcRect);

                        // Alpha blend image
                        BlendFunction blendFunction = new BlendFunction();
                        blendFunction.BlendOp = (byte)BlendOperation.AC_SRC_OVER;  // Only supported blend operation
                        blendFunction.BlendFlags = (byte)BlendFlags.Zero;           // Documentation says put 0 here
                        blendFunction.SourceConstantAlpha = (byte)_alpha;			// Constant alpha factor
                        blendFunction.AlphaFormat = 0;                              // Don't look for per pixel alpha

                        DrawingAPI.AlphaBlend(hdcOrg, bounds.X, bounds.Y, bounds.Width, bounds.Height,
                                              hdcBuffer, 0, 0, (int)imgInfo.Width, (int)imgInfo.Height, blendFunction);

                        gx.ReleaseHdc(hdcOrg);              // Required cleanup to GetHdc()
                        gxBuffer.ReleaseHdc(hdcBuffer);     // Required cleanup to GetHdc()
                    }
                }
            }
		}
    }
}
