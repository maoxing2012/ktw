using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Microsoft.Controls
{
    [Flags]
    public enum TextFormatFlags
    {
        // Summary: 
        //     Applies the default formatting, which is left-aligned. 
        Default = 0,
        // 
        // Summary: 
        //     Aligns the text on the top of the bounding rectangle. 
        Top = 0,
        // 
        // Summary: 
        //     Aligns the text on the left side of the clipping area. 
        Left = 0,
        // 
        // Summary: 
        //     Adds padding to the bounding rectangle to accommodate overhanging glyphs. 
        GlyphOverhangPadding = 0,
        // 
        // Summary: 
        //     Centers the text horizontally within the bounding rectangle. 
        HorizontalCenter = 1,
        // 
        // Summary: 
        //     Aligns the text on the right side of the clipping area. 
        Right = 2,
        // 
        // Summary: 
        //     Centers the text vertically, within the bounding rectangle. 
        VerticalCenter = 4,
        // 
        // Summary: 
        //     Aligns the text on the bottom of the bounding rectangle. Applied only when 
        //     the text is a single line. 
        Bottom = 8,
        // 
        // Summary: 
        //     Breaks the text at the end of a word. 
        WordBreak = 16,
        // 
        // Summary: 
        //     Displays the text in a single line. 
        SingleLine = 32,
        // 
        // Summary: 
        //     Expands tab characters. The default number of characters per tab is eight. 
        //     The System.Windows.Forms.TextFormatFlags.WordEllipsis, System.Windows.Forms.TextFormatFlags.PathEllipsis, 
        //     and System.Windows.Forms.TextFormatFlags.EndEllipsis values cannot be used 
        //     with System.Windows.Forms.TextFormatFlags.ExpandTabs. 
        ExpandTabs = 64,
        // 
        // Summary: 
        //     Allows the overhanging parts of glyphs and unwrapped text reaching outside 
        //     the formatting rectangle to show. 
        NoClipping = 256,
        // 
        // Summary: 
        //     Includes the font external leading in line height. Typically, external leading 
        //     is not included in the height of a line of text. 
        ExternalLeading = 512,
        // 
        // Summary: 
        //     Turns off processing of prefix characters. Typically, the ampersand (&) mnemonic-prefix 
        //     character is interpreted as a directive to underscore the character that 
        //     follows, and the double-ampersand (&&) mnemonic-prefix characters as a directive 
        //     to print a single ampersand. By specifying System.Windows.Forms.TextFormatFlags.NoPrefix, 
        //     this processing is turned off. For example, an input string of "A&bc&&d" 
        //     with System.Windows.Forms.TextFormatFlags.NoPrefix applied would result in 
        //     output of "A&bc&&d". 
        NoPrefix = 2048,
        // 
        // Summary: 
        //     Uses the system font to calculate text metrics. 
        Internal = 4096,
        // 
        // Summary: 
        //     Specifies the text should be formatted for display on a System.Windows.Forms.TextBox 
        //     control. 
        TextBoxControl = 8192,
        // 
        // Summary: 
        //     Removes the center of trimmed lines and replaces it with an ellipsis. 
        PathEllipsis = 16384,
        // 
        // Summary: 
        //     Removes the end of trimmed lines, and replaces them with an ellipsis. 
        EndEllipsis = 32768,
        // 
        // Summary: 
        //     Has no effect on the drawn text. 
        ModifyString = 65536,
        // 
        // Summary: 
        //     Displays the text from right to left. 
        RightToLeft = 131072,
        // 
        // Summary: 
        //     Trims the line to the nearest word and an ellipsis is placed at the end of 
        //     a trimmed line. 
        WordEllipsis = 262144,
        // 
        // Summary: 
        //     Applies to Windows 98, Windows Me, Windows 2000, or Windows XP only: 
        NoFullWidthCharacterBreak = 524288,
        // 
        // Summary: 
        //     Applies to Windows 2000 and Windows XP only: 
        HidePrefix = 1048576,
        // 
        // Summary: 
        //     Applies to Windows 2000 or Windows XP only: 
        PrefixOnly = 2097152,
        // 
        // Summary: 
        //     Preserves the clipping specified by a System.Drawing.Graphics object. Applies 
        //     only to methods receiving an System.Drawing.IDeviceContext that is a System.Drawing.Graphics. 
        PreserveGraphicsClipping = 16777216,
        // 
        // Summary: 
        //     Preserves the transformation specified by a System.Drawing.Graphics. Applies 
        //     only to methods receiving an System.Drawing.IDeviceContext that is a System.Drawing.Graphics. 
        PreserveGraphicsTranslateTransform = 33554432,
        // 
        // Summary: 
        //     Does not add padding to the bounding rectangle. 
        NoPadding = 268435456,
        // 
        // Summary: 
        //     Adds padding to both sides of the bounding rectangle. 
        LeftAndRightPadding = 536870912,
    }
}