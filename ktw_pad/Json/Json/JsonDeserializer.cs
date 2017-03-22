using Json.Helpers;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;
using System.ComponentModel;
namespace Json
{
	public class JsonDeserializer
	{
		private static readonly Type _IEnumerableType = typeof(IEnumerable);
		private readonly JsonReader _reader;
		private readonly string _fieldPrefix;
		public JsonDeserializer(JsonReader reader) : this(reader, string.Empty)
		{
		}
		public JsonDeserializer(JsonReader reader, string fieldPrefix)
		{
			this._reader = reader;
			this._fieldPrefix = fieldPrefix;
		}
		public static T Deserialize<T>(JsonReader reader)
		{
			return JsonDeserializer.Deserialize<T>(reader, string.Empty);
		}
		public static T Deserialize<T>(JsonReader reader, string fieldPrefix)
		{
			return (T)((object)new JsonDeserializer(reader, fieldPrefix).DeserializeValue(typeof(T)));
		}
		private object DeserializeValue(Type type)
		{
			this._reader.SkipWhiteSpaces();
			bool isNullable = false;
			if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(Nullable<>))
			{
				isNullable = true;
				type = Nullable.GetUnderlyingType(type);
			}
			object result;
			if (type == typeof(byte))
			{
				result = this._reader.ReadByte(isNullable);
			}
			else
			{
				if (type == typeof(int))
				{
					result = this._reader.ReadInt32(isNullable);
				}
				else
				{
					if (type == typeof(string))
					{
						result = this._reader.ReadString();
					}
					else
					{
						if (type == typeof(double))
						{
							result = this._reader.ReadDouble(isNullable);
						}
						else
						{
							if (type == typeof(DateTime))
							{
								result = this._reader.ReadDateTime(isNullable);
							}
							else
							{
								if (type.Name == "Dictionary`2")
								{
									result = this.DeserializeDict(type);
								}
								else
								{
									if (JsonDeserializer._IEnumerableType.IsAssignableFrom(type))
									{
										result = this.DeserializeList(type);
									}
									else
									{
										if (type == typeof(char))
										{
											result = this._reader.ReadChar();
										}
										else
										{
											if (type == typeof(bool))
											{
												result = this._reader.ReadBool(isNullable);
											}
											else
											{
												if (type.IsEnum)
												{
													result = this._reader.ReadEnum(type);
												}
												else
												{
													if (type == typeof(long))
													{
														result = this._reader.ReadInt64(isNullable);
													}
													else
													{
														if (type == typeof(float))
														{
															result = this._reader.ReadFloat(isNullable);
														}
														else
														{
															if (type == typeof(decimal))
															{
																result = this._reader.ReadDecimal(isNullable);
															}
															else
															{
                                                                if (type == typeof(short))
                                                                {
                                                                    result = this._reader.ReadInt16(isNullable);
                                                                }
                                                                else
                                                                {
                                                                    char nextChar = _reader.Peek();
                                                                    if ('"' == nextChar)
                                                                    {
                                                                        result = this._reader.ReadString();
                                                                    }
                                                                    else if ('{' == nextChar || 'n' == nextChar)
                                                                    {
                                                                        if (type == typeof(object))
                                                                        {
                                                                            //result = this.DeserializeDict(typeof(Dictionary<,>));
                                                                            result = this.DeserializeDict(typeof(Dictionary<string, object>));
                                                                        }
                                                                        else
                                                                        {
                                                                            result = this.ParseObject(type);
                                                                        }
                                                                    }
                                                                    else if ('[' == nextChar)
                                                                    {
                                                                        if (type == typeof(object))
                                                                        {
                                                                            result = this.DeserializeList(typeof(BindingList<Dictionary<string, object>>));
                                                                        }
                                                                        else
                                                                        {
                                                                            result = this.DeserializeList(type);
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                        result = this._reader.ReadDecimal(isNullable);
                                                                    }
                                                                }
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			return result;
		}
		private object DeserializeDict(Type dictType)
		{
			this._reader.SkipWhiteSpaces();
            if (this._reader.Peek() == 'n')
            {
                this._reader.AssertAndConsumeNull();
                this._reader.SkipWhiteSpaces();
                return null;
            }
			this._reader.AssertAndConsume('{');
			Type type;
			Type type2;
			bool flag;
			IDictionary dictionary = DictHelper.CreateContainer(dictType, out type, out type2, out flag);
			this._reader.SkipWhiteSpaces();
			object result;
			if (this._reader.Peek() == '}')
			{
				this._reader.Read();
				result = dictionary;
			}
			else
			{
				do
				{
					object key = this.DeserializeValue(type);
					this._reader.SkipWhiteSpaces();
					this._reader.AssertAndConsume(':');
					object value = this.DeserializeValue(type2);
					this._reader.SkipWhiteSpaces();
					dictionary.Add(key, value);
				}
				while (!this._reader.AssertNextIsDelimiterOrSeparator('}'));
				if (flag)
				{
					result = dictType.GetConstructor(BindingFlags.Instance | BindingFlags.Public, null, new Type[]
					{
						dictionary.GetType()
					}, null).Invoke(new object[]
					{
						dictionary
					});
				}
				else
				{
					result = dictionary;
				}
			}
			return result;
		}
		private object DeserializeList(Type listType)
		{
			this._reader.SkipWhiteSpaces();
            Type listItemType = ListHelper.GetListItemType(listType);
            bool flag;
            IList list = ListHelper.CreateContainer(listType, listItemType, out flag);
            object result;
            if (this._reader.Peek() == 'n')
            {
                //"{\"class\":\"com.core.scpwms.server.mobile.bean.MobileResponse\",\"results\":{\"asnList\":null},\"severityMsg\":null,\"severityMsgType\":\"M\",\"targetPageId\":null}"
                //\"results\":{\"asnList\":null}
                this._reader.Read();
                this._reader.Read();
                this._reader.Read();
                this._reader.Read();
                result = list;
                return result;
            }
            this._reader.AssertAndConsume('[');
			this._reader.SkipWhiteSpaces();
			if (this._reader.Peek() == ']')
			{
				this._reader.Read();
				result = list;
			}
			else
			{
				do
				{
					list.Add(this.DeserializeValue(listItemType));
					this._reader.SkipWhiteSpaces();
				}
				while (!this._reader.AssertNextIsDelimiterOrSeparator(']'));
				if (listType.IsArray)
				{
					result = ListHelper.ToArray((List<object>)list, listItemType);
				}
				else
				{
					if (flag)
					{
						result = listType.GetConstructor(BindingFlags.Instance | BindingFlags.Public, null, new Type[]
						{
							list.GetType()
						}, null).Invoke(new object[]
						{
							list
						});
					}
					else
					{
						result = list;
					}
				}
			}
			return result;
		}
		private object ParseObject(Type type)
		{
            if (this._reader.Peek() == '{')
            {
                this._reader.AssertAndConsume('{');
                if (this._reader.Peek() == '}')
                {
                    this._reader.SkipWhiteSpaces();
                    if (this._reader.AssertNextIsDelimiterOrSeparator('}'))
                    {
                        return type.Assembly.CreateInstance(type.FullName);
                    }
                }
            }
            else if (this._reader.Peek() == 'n')
            {
                this._reader.AssertAndConsumeNull();
                this._reader.SkipWhiteSpaces();
                return type.Assembly.CreateInstance(type.FullName);
            }
			ConstructorInfo defaultConstructor = ReflectionHelper.GetDefaultConstructor(type);
			object obj = defaultConstructor.Invoke(null);
			while (true)
			{
				this._reader.SkipWhiteSpaces();
				string text = this._reader.ReadString();
				if (!text.StartsWith(this._fieldPrefix))
				{
					text = this._fieldPrefix + text;
				}
                if ("class".Equals(text))
                {
                    this._reader.SkipWhiteSpaces();
                    this._reader.AssertAndConsume(':');
                    this._reader.SkipWhiteSpaces();
                    text = this._reader.ReadString();
                    this._reader.SkipWhiteSpaces();
                    if (this._reader.AssertNextIsDelimiterOrSeparator('}'))
                    {
                        break;
                    }
                    continue;
                }
                //java和.net的属性首字母大小写不一致
				PropertyInfo propertyInfo = ReflectionHelper.FindProp(type, text);
                if (propertyInfo == null)
                {
                    text = text.Substring(0, 1).ToUpper() + text.Substring(1);
                    propertyInfo = ReflectionHelper.FindProp(type, text);
                }
				this._reader.SkipWhiteSpaces();
				this._reader.AssertAndConsume(':');
				this._reader.SkipWhiteSpaces();
                object obj2=null;
                if (propertyInfo == null)
                {
                    //找不到的属性跳过
                    obj2 = this.DeserializeValue(typeof(object));
                }
                else
                {
                    obj2 = this.DeserializeValue(propertyInfo.PropertyType);
                    string name = propertyInfo.PropertyType.Name;
                    if (name == null)
                    {
                        goto IL_E2;
                    }
                    if (!(name == "Dictionary`2"))
                    {
                        if (!(name == "List`1"))
                        {
                            goto IL_E2;
                        }
                        this.setListValue(propertyInfo, obj, obj2);
                    }
                    else
                    {
                        this.setDictValue(propertyInfo, obj, obj2);
                    }
                }
				IL_EF:
				this._reader.SkipWhiteSpaces();
				if (this._reader.AssertNextIsDelimiterOrSeparator('}'))
				{
					break;
				}
				continue;
				IL_E2:
				propertyInfo.SetValue(obj, obj2, null);
				goto IL_EF;
			}
			return obj;
		}
		private void setListValue(PropertyInfo field, object instance, object fieldValue)
		{
			IList list = field.GetValue(instance, null) as IList;
			IList list2 = (IList)fieldValue;
			if (list != null)
			{
				if (list2 != null)
				{
					list.Clear();
					foreach (object current in list2)
					{
						list.Add(current);
					}
				}
			}
			else
			{
				field.SetValue(instance, fieldValue, null);
			}
		}
		private void setDictValue(PropertyInfo field, object instance, object fieldValue)
		{
			IDictionary dictionary = field.GetValue(instance, null) as IDictionary;
			IDictionary dictionary2 = (IDictionary)fieldValue;
			if (dictionary != null)
			{
				if (dictionary2 != null)
				{
					dictionary.Clear();
					foreach (object current in dictionary2.Keys)
					{
						dictionary.Add(current, dictionary2[current]);
					}
				}
			}
			else
			{
				field.SetValue(instance, fieldValue, null);
			}
		}
	}
}
