using System;
using System.Globalization;
using System.IO;
using System.Text;
namespace Json
{
	public class JsonReader : IDisposable
	{
		private readonly TextReader _reader;
		private bool _disposed;
		public JsonReader(TextReader input)
		{
			this._reader = input;
		}
		public JsonReader(Stream input) : this(new StreamReader(input, Encoding.UTF8))
		{
		}
		public JsonReader(string input) : this(new StringReader(input))
		{
		}
		public virtual void SkipWhiteSpaces()
		{
			while (true)
			{
				char c = this.Peek();
				if (!char.IsWhiteSpace(c))
				{
					break;
				}
				this._reader.Read();
			}
		}
		public virtual int? ReadInt32(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new int?(0)) : new int?(Convert.ToInt32(text));
		}
		public virtual byte? ReadByte(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new byte?(0)) : new byte?(Convert.ToByte(text));
		}
		public virtual string ReadString()
		{
			string result;
			if (this.Peek() != '"')
			{
				this.AssertAndConsumeNull();
				result = null;
			}
			else
			{
				this.Read();
				StringBuilder stringBuilder = new StringBuilder(25);
				bool flag = false;
				while (true)
				{
					char c = this.Read();
					if (c == '\\' && !flag)
					{
						flag = true;
					}
					else
					{
						if (flag)
						{
							if (c == 'u')
							{
								stringBuilder.Append(this.HandleEscapedSequence());
							}
							else
							{
								stringBuilder.Append(this.FromEscaped(c));
							}
							flag = false;
						}
						else
						{
							if (c == '"')
							{
								break;
							}
							stringBuilder.Append(c);
						}
					}
				}
				string text = stringBuilder.ToString();
				result = ((text == "null") ? null : text);
			}
			return result;
		}
		public virtual double? ReadDouble(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new double?(0.0)) : new double?(Convert.ToDouble(text));
		}
		public virtual DateTime? ReadDateTime(bool isNullable)
		{
			string text = this.ReadString();
			return new DateTime?((text == null) ? new DateTime(1970, 1, 1) : DateTime.Parse(text));
		}
		public virtual char ReadChar()
		{
			string text = this.ReadString();
			char result;
			if (text == null)
			{
				result = '\0';
			}
			else
			{
				if (text.Length > 1)
				{
					throw new JsonException("Expecting a character, but got a string");
				}
				result = text[0];
			}
			return result;
		}
		public virtual object ReadEnum(Type type)
		{
			return Enum.Parse(type, this.ReadInt64(false).ToString(), false);
		}
		public virtual long? ReadInt64(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new long?(0L)) : new long?(Convert.ToInt64(text));
		}
		public virtual bool? ReadBool(bool isNullable)
		{
			string text = this.ReadNonStringValue('0');
			bool? result;
			if (text == null)
			{
				result = (isNullable ? null : new bool?(false));
			}
			else
			{
				if (text.Equals("true"))
				{
					result = new bool?(true);
				}
				else
				{
					if (!text.Equals("false"))
					{
						throw new JsonException("Expecting true or false, but got " + text);
					}
					result = new bool?(false);
				}
			}
			return result;
		}
		public virtual float? ReadFloat(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new float?(0f)) : new float?(Convert.ToSingle(text));
		}
		public virtual decimal? ReadDecimal(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new decimal?(0m)) : new decimal?(Convert.ToDecimal(text));
		}
		public virtual short? ReadInt16(bool isNullable)
		{
			string text = this.ReadNumericValue();
			return (text == null) ? (isNullable ? null : new short?(0)) : new short?(Convert.ToInt16(text));
		}
		public virtual string ReadNumericValue()
		{
			return this.ReadNonStringValue('0');
		}
		public virtual string ReadNonStringValue(char offset)
		{
			StringBuilder stringBuilder = new StringBuilder(10);
			while (true)
			{
				char c = this.Peek();
				if (this.IsDelimiter(c))
				{
					break;
				}
				int num = this._reader.Read();
				if (num >= 48 && num <= 57)
				{
					stringBuilder.Append(num - (int)offset);
				}
				else
				{
					stringBuilder.Append((char)num);
				}
			}
			string text = stringBuilder.ToString();
			return (text == "null") ? null : text;
		}
		public virtual bool IsDelimiter(char c)
		{
			return c == '}' || c == ']' || c == ',' || this.IsWhiteSpace(c);
		}
		public virtual bool IsWhiteSpace(char c)
		{
			return char.IsWhiteSpace(c);
		}
		public virtual char Peek()
		{
			int c = this._reader.Peek();
			return JsonReader.ValidateChar(c);
		}
		public virtual char Read()
		{
			int c = this._reader.Read();
			return JsonReader.ValidateChar(c);
		}
		private static char ValidateChar(int c)
		{
			if (c == -1)
			{
				throw new JsonException("End of data");
			}
			return (char)c;
		}
		public virtual string FromEscaped(char c)
		{
			if (c <= '\\')
			{
				if (c == '"')
				{
					string result = "\"";
					return result;
				}
				if (c == '/')
				{
					string result = "/";
					return result;
				}
				if (c == '\\')
				{
					string result = "\\";
					return result;
				}
			}
			else
			{
				if (c <= 'f')
				{
					if (c == 'b')
					{
						string result = "\b";
						return result;
					}
					if (c == 'f')
					{
						string result = "\f";
						return result;
					}
				}
				else
				{
					if (c == 'n')
					{
						string result = "\n";
						return result;
					}
					switch (c)
					{
					case 'r':
					{
						string result = "\r";
						return result;
					}
					case 't':
					{
						string result = "\t";
						return result;
					}
					}
				}
			}
			throw new ArgumentException("Unrecognized escape character: " + c);
		}
		protected internal virtual void AssertAndConsume(char character)
		{
			char c = this.Read();
			if (c != character)
			{
				throw new JsonException(string.Format("Expected character '{0}', but got: '{1}'", character, c));
			}
		}
		protected internal virtual void AssertAndConsumeNull()
		{
			if (this.Read() != 'n' || this.Read() != 'u' || this.Read() != 'l' || this.Read() != 'l')
			{
				throw new JsonException("Expected null");
			}
		}
		protected internal bool AssertNextIsDelimiterOrSeparator(char endDelimiter)
		{
			char c = this.Read();
			bool result;
			if (c == endDelimiter)
			{
				result = true;
			}
			else
			{
				if (c != ',')
				{
					throw new JsonException("Expected array separator or end of array, got: " + c);
				}
				result = false;
			}
			return result;
		}
		public void Dispose()
		{
			this.Dispose(true);
			GC.SuppressFinalize(this);
		}
		private void Dispose(bool disposing)
		{
			if (!this._disposed)
			{
				if (disposing)
				{
					this._reader.Close();
				}
				this._disposed = true;
			}
		}
		private char HandleEscapedSequence()
		{
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < 4; i++)
			{
				char c = this.Read();
				if (!JsonReader.IsHexDigit(c))
				{
					throw new JsonException(string.Format("Expected hex digit but got: '{0}'", c));
				}
				stringBuilder.Append(c);
			}
			return (char)int.Parse(stringBuilder.ToString(), NumberStyles.HexNumber);
		}
		private static bool IsHexDigit(char x)
		{
			return (x >= '0' && x <= '9') || (x >= 'a' && x <= 'f') || (x >= 'A' && x <= 'F');
		}
	}
}
