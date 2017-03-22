using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
namespace Json.Helpers
{
	internal static class DictHelper
	{
		private static readonly Type _IReadonlyGenericType = typeof(ReadOnlyCollection<>);
		public static IDictionary CreateContainer(Type dictType, out Type keyType, out Type valueType, out bool isReadOnly)
		{
			isReadOnly = false;
			keyType = null;
			valueType = null;
			Type[] genericArguments = dictType.GetGenericArguments();
			if (genericArguments.Length == 2)
			{
				keyType = genericArguments[0];
				valueType = genericArguments[1];
			}
			if (DictHelper._IReadonlyGenericType.IsAssignableFrom(dictType.GetGenericTypeDefinition()))
			{
				isReadOnly = true;
			}
			return (IDictionary)Activator.CreateInstance(typeof(Dictionary<, >).MakeGenericType(new Type[]
			{
				keyType,
				valueType
			}));
		}
	}
}
