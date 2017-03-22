using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using Entity;
using System.ComponentModel;

namespace Framework
{
    /// <summary>
    /// 
    /// </summary>
    public class BizUtil
    {
        public static StockResult QueryStock(string barcode)
        {
            StockResult ret=new StockResult();
            ret.ActionResult = ActionResult.SelectNone;
            using (frmSkuIdSearch frmNext = new frmSkuIdSearch())
            {
                int retflg = frmNext.Init(false, barcode);
                if (retflg != 0 && retflg != 2)
                {
                    return ret;
                }
                if (frmNext.SelectedSku != null)
                {
                    ret.ActionResult = ActionResult.SelectOne;
                    ret.Result = frmNext.SelectedSku;
                    return ret;
                }
                DialogResult result = frmNext.ShowDialog();
                if (result == DialogResult.OK)
                {
                    ret.ActionResult = ActionResult.SelectOne;
                    ret.Result = frmNext.SelectedSku;
                    return ret;
                }
            }
            return ret;
        }

        /// <summary>
        /// 从一览选择一条
        /// </summary>
        /// <param name="list"></param>
        /// <param name="title"></param>
        /// <returns></returns>
        public static T QueryList<T>(BindingList<T> plist, string title) where T : ListObj
        {
            T ret = null;
            BindingList<ListObj> list = new BindingList<ListObj>();
            for (int i = 0; i < plist.Count; i++)
            {
                list.Add(plist[i]);
            }
            using (frmList frmNext = new frmList())
            {
                int retflg = frmNext.Init(false, list, title);
                if (retflg != 0 && retflg != 2)
                {
                    return ret;
                }
                DialogResult result = frmNext.ShowDialog();
                if (result == DialogResult.OK)
                {
                    ret = frmNext.SelectedItem as T;
                }
            }
            return ret;
        }
    }
}
