using System;
using System.Windows.Forms;


namespace Framework
{
	/// <summary>
	/// This Panel is able to handle alpha channel for its child controls
	/// inheriting from AlphaControl.
	/// </summary>
	public partial class AlphaPanel : Panel
	{
		private AlphaContainer _alphaManager;


		/// <summary>
		/// Default constructor.
		/// </summary>
		public AlphaPanel()
        {
            // Instantiate before the components to handle events triggered by InitializeComponent
            _alphaManager = new AlphaContainer(this);

            InitializeComponent();
		}


		protected override void OnResize(EventArgs e)
		{
            try
            {
                _alphaManager.OnResize(e);
                base.OnResize(e);
            }
            catch
            {
            }
		}

		protected override void OnPaintBackground(PaintEventArgs e)
		{
            try
            {
                // Prevent flicker, we will take care of the background in OnPaint()
                if (FormUtil.DesignMode(this))
                    base.OnPaintBackground(e);
            }
            catch
            {
            }
		}
		protected override void OnPaint(PaintEventArgs e)
		{
            try
            {
                if (this.IsDesignMode())
                {
                    base.OnPaint(e);
                }
                else
                _alphaManager.OnPaint(e);
            }
            catch
            {
            }
		}
	}
}
