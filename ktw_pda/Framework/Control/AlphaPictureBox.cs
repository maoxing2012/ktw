using System.Drawing;


namespace Framework
{
	/// <summary>
	/// Simple PictureBox control handling alpha channel.
	/// </summary>
	public class AlphaPictureBox : AlphaControl
	{
		private AlphaImage _image;
		private uint _alpha = 0;

        /// <summary>
        /// The image to draw.
        /// </summary>
		public AlphaImage Image
		{
			get { return _image; }
			set
			{
				_image = value;
				if (_image != null)
					_image.Alpha = _alpha;
			}
		}

        /// <summary>
        /// The Alpha channel for the image.
        /// </summary>
		public uint Alpha
		{
			get { return _alpha; }
			set
			{
				_alpha = value;
				if (_image != null)
					_image.Alpha = _alpha;
			}
        }

        /// <summary>
        /// Cleaning.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (_image != null)
                    _image.Dispose();
            }
            base.Dispose(disposing);
        }


        #region AlphaControl Members

        /// <summary>
        /// Draws the image if any.
        /// </summary>
        /// <param name="gx"></param>
        public override void Draw(Graphics gx)
		{
			if (_image != null)
				_image.Draw(gx, this.Bounds);
		}

		#endregion
	}
}
