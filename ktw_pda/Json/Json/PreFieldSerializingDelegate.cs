using System;
namespace Json
{
	public delegate bool PreFieldSerializingDelegate(string name, ref object value);
}
