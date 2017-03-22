using Json.Helpers;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;
namespace Json
{
	public class JsonSerializer
	{
		private readonly JsonWriter _writer;
		private readonly string _fieldPrefix;
		private ArrayList _currentGraph;
		private readonly PreFieldSerializingDelegate _callback;
		public JsonSerializer(JsonWriter writer, PreFieldSerializingDelegate callback) : this(writer, string.Empty, callback)
		{
		}
		public JsonSerializer(JsonWriter writer, string fieldPrefix, PreFieldSerializingDelegate callback)
		{
			this._writer = writer;
			this._currentGraph = new ArrayList(0);
			this._fieldPrefix = fieldPrefix;
			this._callback = callback;
		}
		public static void Serialize(JsonWriter writer, object instance)
		{
			JsonSerializer.Serialize(writer, instance, string.Empty);
		}
		public static void Serialize(JsonWriter writer, object instance, string fieldPrefix)
		{
			JsonSerializer.Serialize(writer, instance, fieldPrefix, null);
		}
		public static void Serialize(JsonWriter writer, object instance, PreFieldSerializingDelegate callback)
		{
			JsonSerializer.Serialize(writer, instance, string.Empty, callback);
		}
		public static void Serialize(JsonWriter writer, object instance, string fieldPrefix, PreFieldSerializingDelegate callback)
		{
			new JsonSerializer(writer, fieldPrefix, callback).SerializeValue("root", instance);
		}
		private void SerializeValue(string name, object value)
		{
			if (this._callback != null)
			{
				if (!this._callback(name, ref value))
				{
					return;
				}
			}
			if (value == null)
			{
				this._writer.WriteNull();
			}
			else
			{
				if (value is string)
				{
					this._writer.WriteString((string)value);
				}
				else
				{
					if (value is int || value is long || value is short || value is float || value is byte || value is sbyte || value is uint || value is ulong || value is ushort || value is double || value is decimal)
					{
						this._writer.WriteRaw(value.ToString());
					}
					else
					{
						if (value is char)
						{
							this._writer.WriteChar((char)value);
						}
						else
						{
							if (value is bool)
							{
								this._writer.WriteBool((bool)value);
							}
							else
							{
								if (value is DateTime)
								{
									this._writer.WriteDate((DateTime)value);
								}
								else
								{
									if (value is IDictionary)
									{
										this.SerializeDictionary((IDictionary)value);
									}
									else
									{
										if (value is IEnumerable)
										{
											this.SerializeEnumerable((IEnumerable)value);
										}
										else
										{
											this.SerializeObject(value);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		private void SerializeObject(object @object)
		{
			if (@object != null)
			{
				List<PropertyInfo> serializableFields = ReflectionHelper.GetSerializableFields(@object.GetType());
				if (serializableFields.Count != 0)
				{
					if (this._currentGraph.Contains(@object))
					{
						throw new JsonException("Recursive reference found. Serialization cannot complete. Consider marking the offending field with the NonSerializedAttribute");
					}
					ArrayList currentGraph = this._currentGraph;
					ArrayList currentGraph2 = new ArrayList(this._currentGraph);
					this._currentGraph = currentGraph2;
					this._currentGraph.Add(@object);
					this._writer.BeginObject();
					this.SerializeKeyValue(this.GetKeyName(serializableFields[0]), ReflectionHelper.GetValue(serializableFields[0], @object), true);
					for (int i = 1; i < serializableFields.Count; i++)
					{
						this.SerializeKeyValue(this.GetKeyName(serializableFields[i]), ReflectionHelper.GetValue(serializableFields[i], @object), false);
					}
					this._writer.EndObject();
					this._currentGraph = currentGraph;
				}
			}
		}
		private string findFieldName(string fieldNameData)
		{
			return (fieldNameData[0] == '<') ? fieldNameData.Substring(1, fieldNameData.IndexOf('>') - 1) : fieldNameData;
		}
		private string GetKeyName(MemberInfo field)
		{
			string text = this.findFieldName(field.Name);
			return text.StartsWith(this._fieldPrefix) ? text.Substring(this._fieldPrefix.Length) : text;
		}
		private void SerializeEnumerable(IEnumerable value)
		{
			IEnumerator enumerator = value.GetEnumerator();
			this._writer.BeginArray();
			int num = 0;
			if (enumerator.MoveNext())
			{
				this.SerializeValue(num++.ToString(), enumerator.Current);
			}
			while (enumerator.MoveNext())
			{
				this._writer.SeparateElements();
				this.SerializeValue(num++.ToString(), enumerator.Current);
			}
			this._writer.EndArray();
		}
		private void SerializeDictionary(IDictionary value)
		{
			IDictionaryEnumerator enumerator = value.GetEnumerator();
			this._writer.BeginObject();
			this._writer.NewLine();
			if (enumerator.MoveNext())
			{
				this.SerializeKeyValue(enumerator.Key.ToString(), enumerator.Value, true);
			}
			while (enumerator.MoveNext())
			{
				this.SerializeKeyValue(enumerator.Key.ToString(), enumerator.Value, false);
			}
			this._writer.EndObject();
		}
		private void SerializeKeyValue(string key, object value, bool isFirst)
		{
			if (!isFirst)
			{
				this._writer.SeparateElements();
				this._writer.NewLine();
			}
			this._writer.WriteKey(key);
			this.SerializeValue(key, value);
		}
	}
}
