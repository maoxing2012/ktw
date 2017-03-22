namespace Framework
{
    partial class TextEx
    {
        /// <summary> 
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region 组件设计器生成的代码

        /// <summary> 
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.SuspendLayout();
            // 
            // TextEx
            // 
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.TextEx_KeyDown);
            this.GotFocus += new System.EventHandler(this.TextEx_GotFocus);
            this.TextChanged += new System.EventHandler(this.TextEx_TextChanged);
            this.LostFocus += new System.EventHandler(this.TextEx_LostFocus);
            this.ResumeLayout(false);
        }

        #endregion
    }
}
