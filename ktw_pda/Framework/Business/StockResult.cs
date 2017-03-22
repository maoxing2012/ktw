using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    public struct StockResult
    {
        public ActionResult ActionResult { get; set; }
        public Sku Result { get; set; }
    }

    public enum ActionResult
    {
        NoStock,
        SelectOne,
        SelectNone
    }
}
