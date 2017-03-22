using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    public class ApplicationException:Exception 
    {
        public ApplicationException(string message)
            : base(message)
        {
        }
    }
}
