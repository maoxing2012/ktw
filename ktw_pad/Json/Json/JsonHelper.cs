using System;
using System.IO;
using System.Text;
namespace Json
{
	public class JsonHelper
	{
		public static void Serialize(StringBuilder sb, object instance)
		{
			JsonSerializer.Serialize(new JsonWriter(sb), instance, string.Empty);
		}
		public static void Serialize(MemoryStream ms, object instance)
		{
			JsonSerializer.Serialize(new JsonWriter(ms), instance, string.Empty);
		}
		public static void Serialize(string fileName, object instance)
		{
			using (StreamWriter streamWriter = File.CreateText(fileName))
			{
				JsonSerializer.Serialize(new JsonWriter(streamWriter), instance, string.Empty);
			}
		}
		public static byte[] Serialize(object instance)
		{
			byte[] result;
			using (MemoryStream memoryStream = new MemoryStream())
			{
				JsonSerializer.Serialize(new JsonWriter(memoryStream), instance, string.Empty);
				result = memoryStream.ToArray();
			}
			return result;
		}
		public static T Deserialize<T>(StringBuilder sb)
		{
			return JsonDeserializer.Deserialize<T>(new JsonReader(sb.ToString()));
		}
		public static T Deserialize<T>(MemoryStream ms)
		{
			return JsonDeserializer.Deserialize<T>(new JsonReader(ms));
		}
		public static T Deserialize<T>(byte[] data)
		{
			T result;
			using (MemoryStream memoryStream = new MemoryStream(data))
			{
				result = JsonDeserializer.Deserialize<T>(new JsonReader(memoryStream));
			}
			return result;
		}
		public static T Deserialize<T>(string fileName)
		{
			T result;
			using (StreamReader streamReader = File.OpenText(fileName))
			{
				result = JsonDeserializer.Deserialize<T>(new JsonReader(streamReader));
			}
			return result;
		}
	}
}
