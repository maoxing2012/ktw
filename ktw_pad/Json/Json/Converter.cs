using System;
using System.Globalization;
using System.IO;
namespace Json
{
	public class Converter
	{
		public static void Serialize(Stream output, object instance)
		{
			Converter.Serialize(output, instance, string.Empty);
		}
		public static void Serialize(Stream output, object instance, string fieldPrefix)
		{
			JsonWriter jsonWriter = new JsonWriter(output);
			JsonSerializer.Serialize(jsonWriter, instance, fieldPrefix);
			jsonWriter.Flush();
		}
		public static void Serialize(string file, object instance)
		{
			Converter.Serialize(file, instance, string.Empty);
		}
		public static void Serialize(string file, object instance, string fieldPrefix)
		{
			using (JsonWriter jsonWriter = new JsonWriter(file))
			{
				JsonSerializer.Serialize(jsonWriter, instance, fieldPrefix);
			}
		}
		public static string Serialize(object instance)
		{
			return Converter.Serialize(instance, string.Empty);
		}
		public static string Serialize(object instance, string fieldPrefix)
		{
			string result;
			using (StringWriter stringWriter = new StringWriter(CultureInfo.InvariantCulture))
			{
				using (JsonWriter jsonWriter = new JsonWriter(stringWriter))
				{
					JsonSerializer.Serialize(jsonWriter, instance, fieldPrefix);
					result = stringWriter.ToString();
				}
			}
			return result;
		}
		public static void Serialize(Stream output, object instance, PreFieldSerializingDelegate callback)
		{
			Converter.Serialize(output, instance, string.Empty);
		}
		public static void Serialize(Stream output, object instance, string fieldPrefix, PreFieldSerializingDelegate callback)
		{
			JsonWriter jsonWriter = new JsonWriter(output);
			JsonSerializer.Serialize(jsonWriter, instance, fieldPrefix, callback);
			jsonWriter.Flush();
		}
		public static void Serialize(string file, object instance, PreFieldSerializingDelegate callback)
		{
			Converter.Serialize(file, instance, string.Empty, callback);
		}
		public static void Serialize(string file, object instance, string fieldPrefix, PreFieldSerializingDelegate callback)
		{
			using (JsonWriter jsonWriter = new JsonWriter(file))
			{
				JsonSerializer.Serialize(jsonWriter, instance, fieldPrefix, callback);
			}
		}
		public static string Serialize(object instance, PreFieldSerializingDelegate callback)
		{
			return Converter.Serialize(instance, string.Empty, callback);
		}
		public static string Serialize(object instance, string fieldPrefix, PreFieldSerializingDelegate callback)
		{
			string result;
			using (StringWriter stringWriter = new StringWriter(CultureInfo.InvariantCulture))
			{
				using (JsonWriter jsonWriter = new JsonWriter(stringWriter))
				{
					JsonSerializer.Serialize(jsonWriter, instance, fieldPrefix, callback);
					result = stringWriter.ToString();
				}
			}
			return result;
		}
		public static T Deserialize<T>(Stream input)
		{
			return Converter.Deserialize<T>(input, string.Empty);
		}
		public static T Deserialize<T>(Stream input, string fieldPrefix)
		{
			T result;
			using (JsonReader jsonReader = new JsonReader(input))
			{
				result = JsonDeserializer.Deserialize<T>(jsonReader);
			}
			return result;
		}
		public static T DeserializeFromFile<T>(string file)
		{
			return Converter.DeserializeFromFile<T>(file, string.Empty);
		}
		public static T DeserializeFromFile<T>(string file, string fieldPrefix)
		{
			T result;
			using (JsonReader jsonReader = new JsonReader(new FileStream(file, FileMode.Open, FileAccess.Read)))
			{
				result = JsonDeserializer.Deserialize<T>(jsonReader);
			}
			return result;
		}
		public static T Deserialize<T>(string json)
		{
			return Converter.Deserialize<T>(json, string.Empty);
		}
		public static T Deserialize<T>(string json, string fieldPrefix)
		{
            try
            {
#if DEBUG
                System.Diagnostics.Debug.WriteLine("Start:" + System.DateTime.Now.Ticks.ToString());
#endif
                T result;
                using (StringReader stringReader = new StringReader(json))
                {
                    using (JsonReader jsonReader = new JsonReader(stringReader))
                    {
                        result = JsonDeserializer.Deserialize<T>(jsonReader, fieldPrefix);
                    }
                }
                return result;
            }
            finally
            {
#if DEBUG
                System.Diagnostics.Debug.WriteLine("End:" + System.DateTime.Now.Ticks.ToString());
#endif
            }
		}
	}
}
