using System;
using System.Collections.Generic;
using System.Reflection;
namespace Json.Helpers
{
	internal static class ReflectionHelper
	{
		private static readonly Type _nonSerializableAttributeType = typeof(NonSerializedAttribute);
		private static readonly Type _nullableType = typeof(Nullable<>);
		public static List<PropertyInfo> GetSerializableFields(Type type)
		{
			List<PropertyInfo> list = new List<PropertyInfo>(10);
			list.AddRange(type.GetProperties());
			return list;
		}
		public static bool ShouldSerializeField(ICustomAttributeProvider field)
		{
			return field.GetCustomAttributes(ReflectionHelper._nonSerializableAttributeType, true).Length <= 0;
		}
		public static PropertyInfo FindProp(Type type, string name)
		{
			return ReflectionHelper.FindFieldThroughoutHierarchy(type, name);
		}
		public static PropertyInfo FindFieldThroughoutHierarchy(Type type, string name)
		{
			return type.GetProperty(name);
		}
		public static object GetValue(PropertyInfo field, object @object)
		{
			object value = field.GetValue(@object, null);
			bool isEnum = field.PropertyType.IsEnum;
			if (field.PropertyType.IsGenericType && field.PropertyType.GetGenericTypeDefinition() == ReflectionHelper._nullableType && value != null)
			{
				isEnum = Nullable.GetUnderlyingType(field.PropertyType).IsEnum;
			}
			return (isEnum && value != null) ? int.Parse(((Enum)value).ToString("d")) : value;
		}
		public static ConstructorInfo GetDefaultConstructor(Type type)
		{
			ConstructorInfo constructor = type.GetConstructor(BindingFlags.Instance | BindingFlags.NonPublic | BindingFlags.Public, null, new Type[0], null);
			if (constructor == null)
			{
				throw new JsonException(type.FullName + " must have a parameterless constructor");
			}
			return constructor;
		}
	}
}
