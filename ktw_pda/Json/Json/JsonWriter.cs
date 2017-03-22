using System;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Text;
namespace Json
{
	public class JsonWriter : IDisposable
	{
		private const int _indentationSpaces = 2;
		private bool _disposed;
		private int _currentIndentation;
		private readonly TextWriter _writer;
		public JsonWriter(TextWriter output)
		{
			this._writer = output;
		}
		public JsonWriter(Stream output) : this(new StreamWriter(output, Encoding.UTF8))
		{
		}
		public JsonWriter(string file) : this(new FileStream(file, FileMode.Create, FileAccess.Write, FileShare.Read))
		{
		}
		public JsonWriter(StringBuilder output) : this(new StringWriter(output, CultureInfo.InvariantCulture))
		{
		}
		public virtual void WriteNull()
		{
			this.WriteRaw("null");
		}
		public virtual void WriteString(string value)
		{
			this._writer.Write('"' + value.Replace("\"", "\\\"") + '"');
		}
		public virtual void WriteRaw(string value)
		{
			this._writer.Write(value);
		}
		public virtual void WriteChar(char value)
		{
			this.WriteString(value.ToString());
		}
		public virtual void WriteBool(bool value)
		{
			this.WriteRaw(value ? "true" : "false");
		}
		public virtual void WriteDate(DateTime date)
		{
			this.WriteString(date.ToString("yyyy-MM-dd HH:mm:ss"));
		}
		public virtual void WriteKey(string key)
		{
			this.Indent();
			this.WriteString(key);
			this._writer.Write(':');
		}
		public virtual void BeginObject()
		{
			this.NewLineAndIndent();
			this._writer.Write('{');
			this.NewLine();
			this._currentIndentation++;
		}
		public virtual void EndObject()
		{
			this.NewLine();
			this._currentIndentation--;
			this.Indent();
			this._writer.Write('}');
		}
		public virtual void EndArray()
		{
			this._writer.Write(']');
		}
		public virtual void BeginArray()
		{
			this._writer.Write('[');
		}
		public virtual void SeparateElements()
		{
			this._writer.Write(',');
		}
		[Conditional("DEBUG")]
		public virtual void NewLineAndIndent()
		{
            //this.NewLine();
            //this.Indent();
		}
		[Conditional("DEBUG")]
		public virtual void Indent()
		{
            //this._writer.Write(new string(' ', 2 * this._currentIndentation));
		}
		[Conditional("DEBUG")]
		public virtual void NewLine()
		{
            //this._writer.Write('\n');
		}
		public void Flush()
		{
			this._writer.Flush();
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
					this.Flush();
					this._writer.Close();
				}
				this._disposed = true;
			}
		}
	}
}
