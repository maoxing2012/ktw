using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Reflection;
namespace Json.Helpers
{
	internal static class ListHelper
	{
		private static readonly Type _IReadonlyGenericType = typeof(ReadOnlyCollection<>);
		public static Type GetListItemType(Type enumerableType)
		{
			Type result;
			if (enumerableType.IsArray)
			{
				result = enumerableType.GetElementType();
			}
			else
			{
				if (enumerableType.IsGenericType)
				{
					result = enumerableType.GetGenericArguments()[0];
				}
				else
				{
					result = typeof(object);
				}
			}
			return result;
		}
		public static Array ToArray(List<object> container, Type itemType)
		{
			Array array = Array.CreateInstance(itemType, container.Count);
			Array.Copy(container.ToArray(), 0, array, 0, container.Count);
			return array;
		}
		public static IList CreateContainer(Type listType, Type listItemType, out bool isReadOnly)
		{
			isReadOnly = false;
			IList result;
			if (listType.IsArray)
			{
				result = new List<object>();
			}
			else
			{
				if (listType.IsInterface)
				{
					result = (IList)Activator.CreateInstance(typeof(List<>).MakeGenericType(new Type[]
					{
						listItemType
					}));
				}
				else
				{
					if (listType.GetConstructor(BindingFlags.Instance | BindingFlags.Public, null, new Type[0], null) != null)
					{
						result = (IList)Activator.CreateInstance(listType);
					}
					else
					{
						if (ListHelper._IReadonlyGenericType.IsAssignableFrom(listType.GetGenericTypeDefinition()))
						{
							isReadOnly = true;
							result = (IList)Activator.CreateInstance(typeof(List<>).MakeGenericType(new Type[]
							{
								listItemType
							}));
						}
						else
						{
							result = new List<object>();
						}
					}
				}
			}
			return result;
		}
		internal static void GetPropType(Type fieldType, out bool isArray, out bool isDict)
		{
			isDict = (fieldType.Name == "Dictionary`2");
			isArray = fieldType.IsArray;
		}
	}
}
