package com.core.scpwms.server.util;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

/**
 * 文字列を操作するためのユーティリティクラス。
 * 
 * @author 邵華 2005/5/12
 * @version 1.0
 */
public abstract class StringUtil4Jp {

    /** エンコード:MS932 */
    private static final String ENCODE_MS932 = "MS932";

    /**
     * str 引数の文字数長が from、to 引数で指定された間にあるなら true を 間にないなら false を返す。
     * 
     * @param from 最小桁数
     * @param to 最大桁数
     * @param str 判定する文字列
     * @return 文字数長の判定結果
     */
    public static boolean checkStringLength(int from, int to, String str) {
        int len = str.length();
        if (from > to || from > len || to < len) {
            return false;
        }
        return true;
    }

    /**
     * CSV文字列の生成
     * 
     * @param str
     * @return
     */
    public static String makeCsvDataString(String str) {
        if (str != null) {
            str = "'" + str + "'";
        }
        return str;
    }

    /**
     * CSV数字の生成
     * 
     * @param str
     * @return
     */
    public static String makeCsvDataNumber(String str) {
        if (checkHankakuDigit(str)) {
            str = String.valueOf(Long.parseLong(str));
        }
        return str;
    }

    /**
     * str 引数の文字数長が maxLength 引数以下であれば true を 超えた場合 false を返す。
     * 
     * @param maxLength 最大桁数
     * @param str 判定する文字列
     * @return 文字数長の判定結果
     */
    public static boolean checkStringLength(int maxLength, String str) {
        int len = str.length();

        if (len > maxLength) {
            return false;
        }

        return true;
    }

    /**
     * short 型の文字列表示を生成する。
     * 
     * @param num コンバートする short の値
     * @return 引数の文字列表示
     */
    public static String toString(short num) {
        return String.valueOf(num);
    }

    /**
     * int 型の文字列表示を生成する。
     * 
     * @param num コンバートする int の値
     * @return 引数の文字列表示
     */
    public static String toString(int num) {
        return String.valueOf(num);
    }

    /**
     * long 型の文字列表示を生成する。
     * 
     * @param num コンバートする long の値
     * @return 引数の文字列表示
     */
    public static String toString(long num) {
        return String.valueOf(num);
    }

    /**
     * float 型の文字列表示を生成する。
     * 
     * @param num コンバートする float の値
     * @return 引数の文字列表示
     */
    public static String toString(float num) {
        return String.valueOf(num);
    }

    /**
     * double 型の文字列表示を生成する。
     * 
     * @param num コンバートする double の値
     * @return 引数の文字列表示
     */
    public static String toString(double num) {
        return String.valueOf(num);
    }

    /**
     * 引数のString型の文字がAscii文字か判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (Ascii文字 = true それ以外 = false)
     */
    public static boolean checkAscii(char ch) {
        // 1) Ascii文字かどうか判断し、返り値とする
        return ch >= 0x0000 && ch <= 0x007f;
    }

    /**
     * BigDecimal型の文字列表示を生成する。
     * 
     * @param dec BigDecimalの値
     * @return 引数の文字列表示
     */
    public static String toString(BigDecimal dec) {
        if (dec == null) {
            return null;
        }
        return dec.toString();
    }

    /**
     * 引数のString型の文字がすべてAscii文字か判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべてAscii文字 = true それ以外 = false)
     */
    public static boolean checkAscii(String str) {
        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型がAscii文字でなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkAscii(chArray[count])) {
                return false;
            }
        }

        // 4) すべてAscii文字ならtrueを返す
        return true;
    }

    /**
     * 引数のchar型の文字が半角数値か判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (半角数値 = true それ以外 = false)
     */
    public static boolean checkHankakuDigit(char ch) {

        // 1) 半角数値かどうか判断し、返り値とする
        return ch >= 0x0030 && ch <= 0x0039;
    }

    /**
     * 引数のString型の文字列がすべて半角数値か判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて半角数値 = true それ以外 = false)
     */
    public static boolean checkHankakuDigit(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が半角数値でなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkHankakuDigit(chArray[count])) {
                return false;
            }
        }

        // 4) すべて半角数値ならtrueを返す
        return true;
    }

    /**
     * 引数のchar型の文字が全角数値か判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (全角数値 = true それ以外 = false)
     */
    public static boolean checkZenkakuDigit(char ch) {

        // 1) 全角数値かどうか判断し、返り値とする
        return ch >= 0xff10 && ch <= 0xff19;
    }

    /**
     * 引数のString型の文字列がすべて全角数値か判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて全角数値 = true それ以外 = false)
     */
    public static boolean checkZenkakuDigit(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が全角数値でなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkZenkakuDigit(chArray[count])) {
                return false;
            }
        }
        // 4) すべて全角数値ならtrueを返す
        return true;
    }

    /**
     * 引数のchar型の文字が全角アルファベットの大文字か判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (全角アルファベットの大文字 = true それ以外 = false)
     */
    public static boolean checkZenkakuUpperCaseAlphabet(char ch) {

        // 全角アルファベットの大文字かどうか判断し、返り値とする
        return ch >= 0xff21 && ch <= 0xff3a;
    }

    /**
     * 引数のString型の文字列がすべて全角アルファベットの大文字か判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて全角アルファベットの大文字 = true それ以外 = false)
     */
    public static boolean checkZenkakuUpperCaseAlphabet(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が全角アルファベットの大文字でなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkZenkakuUpperCaseAlphabet(chArray[count])) {
                return false;
            }
        }

        // 4) すべて全角アルファベットの大文字ならtrueを返す
        return true;
    }

    /**
     * 引数のchar型の文字が全角アルファベットか判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (全角アルファベット = true それ以外 = false)
     */
    public static boolean checkZenkakuAlphabet(char ch) {

        // 全角アルファベットかどうか判断し、返り値とする
        return ch >= 0xff21 && ch <= 0xff3a || ch >= 0xff41 && ch <= 0xff5a;
    }

    /**
     * 引数のString型の文字列がすべて全角アルファベットか判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて全角アルファベット = true それ以外 = false)
     */
    public static boolean checkZenkakuAlphabet(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が全角アルファベットでなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkZenkakuAlphabet(chArray[count])) {
                return false;
            }
        }

        // 4) すべて全角アルファベットならtrueを返す
        return true;
    }

    /**
     * 引数のchar型の文字が半角カタカナか判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (半角カタカナ = true それ以外 = false)
     */
    public static boolean checkHankakuKana(char ch) {
        // 1)半角カタカナかどうか判断し、返り値とする。
        return 0xff61 <= ch && ch <= 0xff9f;
    }

    /**
     * 引数のString型の文字列がすべて半角カタカナか判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて半角カタカナ = true それ以外 = false)
     */
    public static boolean checkHankakuKana(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が半角カタカナでなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkHankakuKana(chArray[count])) {
                return false;
            }
        }

        // 4) すべて半角カタカナならtrueを返す
        return true;
    }

    /**
     * 引数のString型の文字列に１文字でも半角カタカナがあるか判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (半角カタカナが含まれる = true それ以外 = false)
     */
    public static boolean checkIncludeHankakuKana(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型のうち半角カタカナが含まれていればtrueを返す
        for (int count = 0; count < chArray.length; count++) {
            if (checkHankakuKana(chArray[count])) {
                return true;
            }
        }

        // 4) すべて半角カタカナ以外ならfalseを返す
        return false;
    }

    /**
     * 引数のchar型の文字が全角カタカナか判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (全角カタカナ = true それ以外 = false)
     */
    public static boolean checkKana(char ch) {
        // 1)全角カタカナかどうか判断し、返り値とする。
        // なお、全角スペース(' ' \u3000)、全角延ばし('ー' \30fc)、
        // 全角マイナス('－' \u2212)も全角カタカナと判定する
        return ch >= 0x30a1 && ch <= 0x30f6 || ch == 0x3000 // 全角スペース
                || ch == 0x30fc // 全角延ばし
                || ch == 0x2212 // 全角マイナス UNICODE(SJIS)
                || ch == 0xff0d; // 全角マイナス UNICODE(MS932)
    }

    /**
     * 引数のString型の文字がすべて全角カタカナか判定する
     * 
     * @param str 判定文字列
     * @return 判定結果 (すべて全角カタカナ = true それ以外 = false)
     */
    public static boolean checkKana(String str) {
        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が全角カタカナでなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkKana(chArray[count])) {
                return false;
            }
        }

        // 4) すべて全角カタカナならtrueを返す
        return true;
    }

    /**
     * 引数のString型の文字列がすべてフリガナとして適当か、判定する <BR>
     * フリガナとして適当なものは、全角カタカナ、全角アルファベットの大文字、 <BR>
     * 全角スペース(' ' \u3000)、全角延ばし('ー' \u30fc)、全角マイナス('－' \u2212)とする
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべてフリガナとして適当 = true それ以外 = false)
     */
    public static boolean checkFurigana(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型がフリガナとして適当でなければfalseを返す
        // (全角スペース、全角延ばし、全角マイナスはcheckKANAで判定している)
        for (int count = 0; count < chArray.length; count++) {
            if (!checkKana(chArray[count]) && !checkZenkakuUpperCaseAlphabet(chArray[count])
                    && !checkZenkakuDigit(chArray[count])) {
                return false;
            }
        }

        // 4) すべてフリガナとして適当ならtrueを返す
        return true;
    }

    /**
     * 引数のString型の文字列がすべて全角文字か、判定する 半角文字がある場合falseとする
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて全角文字 = true それ以外 = false)
     */
    public static boolean checkZenkaku(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型の中に
        // 半角文字(アスキー文字および、半角カタカナ)があった場合、falseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (checkAscii(chArray[count]) || checkHankakuKana(chArray[count])) {
                return false;
            }
        }
        // 4) すべてに半角文字がなければtrueを返す
        return true;
    }

    /**
     * 文字列妥当性判定 指定の文字列がヌルまたは、ヌル文字列（""）か判定する。
     * 
     * @param str 判定文字列
     * @return 判定結果（文字列がヌルまたは、ヌル文字列（""）以外の場合は true、そうでない場合は false）
     */
    public static boolean isStr(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * ヌル（null）へヌル文字列（""）転換
     * 
     * @param str 判定文字列
     * @return 転換結果（文字列がヌル（null）以外の場合は 判定文字列、そうでない場合は ヌル文字列（""））
     */
    public static String convertNull(String str) {
        return isStr(str) ? str : "";
    }

    /**
     * 数値かどうかチェックする(小数も含む、符号を入れた場合はfalseを返す)
     * 
     * @param stringNumber チェックする数値の文字列
     * @return boolean 数値かどうか (true: 数値である false 数値でない)
     */
    public static boolean checkNumerical(String stringNumber) {

        // 1) 引数がnullまたは空文字の場合、falseを返す
        if (!isStr(stringNumber)) {
            return false;
        }

        // 2) 小数点が無い場合、整数としてcheckHANKAKU_DIGITでチェックして
        // 返り値をこのメソッドの返り値とする
        if (stringNumber.indexOf(".") == -1) {
            return checkHankakuDigit(stringNumber);
        }

        // 小数点がある場合
        // 3) String型をchar[]型に変換する
        char[] snCharArray = stringNumber.toCharArray();

        // 4) 3)で変換されたchar[]型の配列の先頭または最後に
        // 小数点がある場合はfalseを返す
        if (snCharArray[0] == '.' || snCharArray[snCharArray.length - 1] == '.') {
            return false;
        }

        // 5) 小数点の数を数えるための変数を宣言する
        int decimalCount = 0;

        // 6) 3)で変換されたchar[]型を一文字ずつチェックする、
        // 不適当な文字があった場合は、falseを返す、
        // また、小数点の数もカウントする
        for (int count = 0; count < snCharArray.length; count++) {
            if (snCharArray[count] == '.') {
                decimalCount++;
            }
            if (!checkHankakuDigit(snCharArray[count]) && snCharArray[count] != '.') {
                return false;
            }
        }

        // // 7) 小数点の数が1以下ならtrueそれ以外はfalseを返す
        // return decimalCount < 2;
        return true;
    }

    /**
     * 数値が0以上かどうかチェックする(小数も含む) 注意:このメソッドは数値かどうかのチェックは行わない
     * 
     * @param stringNumber チェックする数値の文字列
     * @return boolean 0以上かどうか (true: 0以上 false それ以外)
     * @throws Exception
     */
    public static boolean checkMoreZero(String stringNumber) throws Exception {

        // 1) Util等のインスタンスを生成する
        // NumberUtil numberUtil = new NumberUtil();

        // 2) 小数点の位置を取得する
        int decimalIndex = stringNumber.indexOf(".");

        // 3) もし引数に小数点が無い場合は、そのままint型に変換し、
        // 0と比較した結果を返り値とする
        if (decimalIndex == -1) {
            return NumberUtil.toInt(stringNumber) >= 0;
        }

        // 小数点がある場合
        // 4) 整数部分と、小数部分に分ける
        String stringInteger = stringNumber.substring(0, decimalIndex);
        // String stringDecimal = stringNumber.substring(decimalIndex + 1);

        // 5) 整数部分の値をint型に変換する
        int intTemp = NumberUtil.toInt(stringInteger);

        // 6) 整数部分が0以上の場合trueを返す
        if (intTemp >= 0) {
            return true;
        }

        // 7) 6)の条件に適合しない場合falseを返す
        return false;
    }

    /**
     * 引数のchar型の文字が半角アルファベットか判定する
     * 
     * @param ch 判定する文字
     * @return 判定結果 (半角アルファベット = true それ以外 = false)
     */
    public static boolean checkHankakuAlphabet(char ch) {

        // 半角アルファベットかどうか判断し、返り値とする
        return (ch >= 0x0041 && ch <= 0x005a) || (ch >= 0x0061 && ch <= 0x007a);
    }

    /**
     * 引数のString型の文字列がすべて半角英字か判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて半角英字 = true それ以外 = false)
     */
    public static boolean checkHankakuAlphabet(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が半角英字でなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkHankakuAlphabet(chArray[count])) {
                return false;
            }
        }

        // 4) すべて全角アルファベットの大文字ならtrueを返す
        return true;
    }

    /**
     * 引数のString型の文字列がすべて半角文字か、判定する 半角文字以外がある場合falseとする
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて半角文字 = true それ以外 = false)
     */
    public static boolean checkHankaku(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型の中に
        // 半角文字(アスキー文字および、半角カタカナ)以外があった場合、falseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkAscii(chArray[count]) && !checkHankakuKana(chArray[count])) {
                return false;
            }
        }
        // 4) すべてに半角文字がなければtrueを返す
        return true;
    }

    /**
     * 引数のString型の文字列がすべて半角英数字か判定する
     * 
     * @param str 判定する文字列
     * @return 判定結果 (すべて半角英数字 = true それ以外 = false)
     */
    public static boolean checkHankakuAlphaDigit(String str) {

        // 1) 引数がnullまたは、長さ0の文字列ならfalseを返す
        if (!isStr(str)) {
            return false;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 2)で取得した配列のすべてのchar型が半角英数字でなければfalseを返す
        for (int count = 0; count < chArray.length; count++) {
            if (!checkHankakuAlphabet(chArray[count]) && !checkHankakuDigit(chArray[count])) {
                return false;
            }
        }

        // 4) すべて半角英数字の場合tureを返す。
        return true;
    }

    /**
     * 引数のString型の文字列上の特殊文字を特殊フォントに変換する
     * 
     * @param str 変換する文字列
     * @return 変換文字列 (変換対象文字"' <>&)
     */
    public static String encodeHTML(String str) {

        char target[] = { '\"', '\'', '<', '>', '&' };
        String specialFont[] = { "&quot;", "&#39;", "&lt;", "&gt;", "&amp;" };

        // 1) 引数がnullまたは、長さ0の文字列ならそのままリターン
        if (!isStr(str)) {
            return str;
        }

        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();
        // 3) 変換後の文字列格納用バッファ
        StringBuffer stb = new StringBuffer(str.length());

        // 4) 変換対象文字を変換
        for (int count = 0; count < chArray.length; count++) {
            int i;
            for (i = 0; i < target.length; i++) {
                if (chArray[count] == target[i]) {
                    break;
                }
            }
            if (i < target.length) {
                stb.append(specialFont[i]);
            } else {
                stb.append(chArray[count]);
            }
        }

        return stb.toString();
    }

    /**
     * str 引数で指定された内容が数値なら true を 数値以外なら false を返す。
     * 
     * @param str 判定する文字列
     * @return 数値の判定結果
     */
    public static boolean checkPositiveNum(String str) {
        return StringUtil4Jp.checkHankakuDigit(str);
    }

    /**
     * String型フォーマット <br>
     * 指定幅に満たない場合、指定文字で埋め文字を行う <br>
     * 
     * @param src フォーマット対象
     * @param width 固定幅
     * @param fillC 埋める文字（1文字）
     * @param fillPattern 埋め文字のパターン
     * @return 変換後の文字列
     *         <p>
     *         fillPattern <br>
     *         1: srcの右側に埋める <br>
     *         2: srcの左側に埋める <br>
     */
    public static String fillFormat(String src, int width, char fillC, int fillPattern) {
        int length = src.length();
        if (length >= width) {
            return src;
        }

        StringBuffer buf = new StringBuffer(width);
        int iDif = width - length;
        for (int i = 0; i < iDif; i++) {
            buf.append(fillC);
        }
        if (fillPattern == 1) { // 右埋め
            buf.insert(0, src);
        } else if (fillPattern == 2) { // 左埋め
            buf.append(src);
        }

        return buf.toString();
    }

    public static String getString( String src , int width ){
    	// srcの長さ
        int length = getByteLength(src);

        String result = src;

        // 指定された長さより長い
        if (length > width) {
        	byte[] oldSrc = null;
        	try{
        		oldSrc = src.getBytes(ENCODE_MS932);
        		result = new String(oldSrc, 0, width, ENCODE_MS932);
        	}catch( UnsupportedEncodingException e ){
        		oldSrc = src.getBytes();
        	}
            if (!src.startsWith(result)) {
                result = new String(oldSrc, 0, width - 1);
            }
        }

        return result;
    }
    
    /**
    * String型フォーマット <br>
    * 指定幅に満たない場合、指定文字で埋め文字を行う <br>
    * 
    * @param src フォーマット対象
    * @param width 固定幅
    * @param fillC 埋める文字（1文字）
    * @param fillPattern 埋め文字のパターン
    * @return 変換後の文字列
    *         <p>
    *         fillPattern <br>
    *         1: srcの右側に埋める <br>
    *         2: srcの左側に埋める <br>
    */
    public static String fillFormatZen(String src, int width, char fillC, int fillPattern) {
        // srcの長さ
        int length = getByteLength(src);

        String result = src;

        // 指定された長さより長い
        if (length > width) {
        	byte[] oldSrc = null;
        	try{
        		oldSrc = src.getBytes(ENCODE_MS932);
        		result = new String(oldSrc, 0, width, ENCODE_MS932);
        	}catch( UnsupportedEncodingException e ){
        		oldSrc = src.getBytes();
        	}
            
            if (!src.startsWith(result)) {
                result = new String(oldSrc, 0, width - 1);
            }
            length = getByteLength(result);
        }

        if (length == width)
            return result;

        StringBuffer buf = new StringBuffer(width);
        int iDif = width - length;
        for (int i = 0; i < iDif; i++) {
            buf.append(fillC);
        }
        if (fillPattern == 1) { // 右埋め
            buf.insert(0, result);
        } else if (fillPattern == 2) { // 左埋め
            buf.append(result);
        }

        return buf.toString();
    }


    /**
     * 与えられた文字列を数値型（長整数の絶対値）に変換して、指定範囲内の桁数かどうかをチェックする。
     * 
     * @param num 対象となる数値
     * @param min 最小桁数
     * @param max 最大桁数
     * @return true: OK false: NG
     */
    public static boolean checkNumericLength(String num, int min, int max) {
        long lng = 0L;
        try {
            lng = Long.parseLong(num);
        } catch (NumberFormatException e) {
            // longに変換不可である場合、falseを返す。
            return false;
        }

        String s = String.valueOf(Math.abs(lng)); // 絶対値をとって文字列に再変換
        if ((s.length() < min) || (s.length() > max)) {
            return false;
        }

        return true;
    }

    /**
     * クロスサイトスクリプティング対応文字列チェック
     * 
     * @param str チェック対象文字列("' <>&)
     * @return 判定結果 true HTMLエンコード文字は含まれていない false HTMLエンコード文字が含まれている
     */
    public static boolean checkHtmlEncodeChar(String str) {

        // チェック対象文字列
        char target[] = { '\"', '\'', '<', '>', '&' };

        // 1) 引数がnullまたは、長さ0の文字列ならtrueをリターン
        if (!isStr(str)) {
            return true;
        }
        // 2) String型をchar[]型に変換する
        char[] chArray = str.toCharArray();

        // 3) 文字列にチェック対象文字列が含まれている場合はfalseをリターン
        for (int count = 0; count < chArray.length; count++) {
            for (int i = 0; i < target.length; i++) {
                if (chArray[count] == target[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 文字列が指定バイト内かチェックする
     * 
     * @param str 対象となる文字列
     * @param bytes 指定するバイト数
     * @return 指定バイト数内の場合は「true」、指定バイト数より大きい場合は「false」
     */
    public static boolean checkByte(String str, int bytes) {

        // 1) nullや空文字はtrue
        if (!isStr(str)) {
            return true;
        }

        // 2) バイトを取得

        byte[] chrByte;
        try {
            chrByte = str.getBytes(ENCODE_MS932);
        } catch (UnsupportedEncodingException e) {
            chrByte = str.getBytes();
        }

        // 3) 文字列が指定バイト数を超えた場合はfalse
        if (chrByte.length > bytes) {
            return false;
        }

        return true;
    }

    /**
     * 
     * <p>処理。</p>
     *
     * @param str
     * @return
     */
    public static int getByteLength(String str) {
        byte[] chrByte;
        try {
            chrByte = str.getBytes(ENCODE_MS932);
        } catch (UnsupportedEncodingException e) {
            chrByte = str.getBytes();
        }

        return chrByte.length;
    }
    
    public static String subString( String str, int fromInx, int toInx ){
    	byte[] chrByte;
        try {
        	byte[] byteStr = str.getBytes(ENCODE_MS932);
            return new String(byteStr, fromInx, toInx - fromInx);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * text文字列内のfrom文字列をすべてto文字列に置き換えた文字列を返す
     * 
     * @param text 対象となる文字列
     * @param from 変換対象文字列
     * @param to 変換文字列
     * @return 変換後の文字列
     */
    public static String replace(String text, String from, String to) {
        if (!isStr(text)) {
            return text;
        }
        String newText = "";
        int pos1 = 0;
        int pos2 = text.indexOf(from);
        while (pos2 >= 0) {
            newText += text.substring(pos1, pos2) + to;
            pos1 = pos2 + from.length();
            pos2 = text.indexOf(from, pos1);
        }
        newText += text.substring(pos1, text.length());
        return newText;
    }

    /**
     * 指定された文字列から指定された文字を削除した結果を返す
     * 
     * @param strTarget 対象となる文字列
     * @param strRemoveChars 削除する文字（複数の指定可）
     * @return 削除結果のString
     */
    public static String removeChars(String strTarget, String strRemoveChars) {
        if (strTarget == null || strTarget.length() == 0 || strRemoveChars == null || strRemoveChars.length() == 0) {
            return strTarget;
        }
        StringBuffer strTmp = new StringBuffer(strTarget.length());
        // 対象文字数長さまでループ
        for (int i = 0; i < strTarget.length(); i++) {
            // 対象文字列から１文字を取る
            String strChar = strTarget.substring(i, i + 1);
            // 削除文字でない場合
            if (-1 == strRemoveChars.indexOf(strChar)) {
                // 有効な文字を追加
                strTmp.append(strChar);
            }
        }
        // Stringで返す
        return strTmp.toString();
    }

    /**
     * text文字列内半角文字・全角文字を判定して指定されたバイト数までの有効文字列を返す
     * 
     * @param text 切り捨て判定を行う文字列
     * @param bytes 切り捨てるバイト長（bytes以内の文字単位で切り捨てが行われる）
     * @return 切り捨てられた有効文字列（対象となる文字列がバイト長以内であれば対象文字列がそのまま返る）
     */
    public static String truncateString(String text, int bytes) {
        if (text == null) {
            return null;
        }
        char charsText[] = text.toCharArray();
        int omissionLen = 0;
        for (int i = 0; i < charsText.length; i++) {
            // 判定文字のバイト数をチェックする（ASCII+半角カナを１バイト範囲とする）
            omissionLen += (charsText[i] >= 0x0000 && charsText[i] <= 0x009f) ? 1 : 2;
            // 指定バイト長以上になった場合は最後の文字判定をして抜ける
            if (omissionLen >= bytes) {
                // 指定バイトを１バイト超えた場合（最後が全角の場合）は最後の漢字文字を含めないで抜ける
                if (omissionLen != bytes) {
                    omissionLen -= 2;
                }
                break;
            }
        }
        // 指定バイト数まで切り捨てて返す
        String value;
        try {
            value = new String(text.getBytes(ENCODE_MS932), 0, omissionLen, ENCODE_MS932);
        } catch (UnsupportedEncodingException e) {
            value = new String(text.getBytes(), 0, omissionLen);
        }
        return value;
    }

    /**
     * 両端に存在する全角、及び半角スペースを除去
     * 
     * @param strValue 変換する文字 メッセージ（内容）
     * @return 変換後文字列
     */
    public static String trimString(String strValue) {
        if (strValue == null || strValue.length() <= 0) {
            return strValue;
        }
        StringBuffer buf = new StringBuffer(0);
        buf.append(strValue.toString());
        int t = 2;
        while (t > 0) {
            for (int i = buf.length(); 0 < i; i--) {

                char c = buf.charAt(i - 1);

                if ((c == '　') || (c == ' ')) {
                    buf.deleteCharAt(i - 1);
                } else
                    break;
            }
            buf = buf.reverse();
            t--;
        }
        String strReturn = buf.toString();
        return strReturn;
    }

    /**
     * 
     * <p>
     * 処理。
     * </p>
     * 
     * @param str
     * @return
     */
    public static boolean isNullOrBlank(String str) {
        if (str != null && str.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 
     * <p>
     * 処理。
     * </p>
     * 
     * @param str
     * @return
     */
    public static String nullToBlank(String str) {
        if (isNullOrBlank(str)) {
            return "";
        }
        return str;
    }

    /**
     * <p>
     * 全角 -> 半角処理。
     * </p>
     * 
     * @param value
     * @return
     */
    public static String convertHalfKana(String value) {
        String vn = value;
        if (vn == null || "".equals(vn)) {
            vn = "";
        }
        vn = toHalfKana(vn);
        vn = toHalfSpace(vn);
        vn = toHalfHaifun(vn);
        vn = toHalfDigit(vn);

        return vn;
    }

    /**
     * <p>
     * 全角カナ名 -> 半角カナ名処理。
     * </p>
     * 
     * @param value
     * @return
     */
    public static String toHalfKana(String value) {
        String result = "";
        StringBuffer tempResult = new StringBuffer();
        if ("".equals(value) || value == null)
            return value;
        for (int i = 0; i < value.length(); i++) {
            if ("。「」、・ヲァィゥェォャュョッーアイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワン゛゜".indexOf(value.charAt(i)) != -1) {
                tempResult.append(convertCharNormal(String.valueOf(value.charAt(i)),
                        "。「」、・ヲァィゥェォャュョッーアイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワン゛゜",
                        "｡｢｣､･ｦｧｨｩｪｫｬｭｮｯｰｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜﾝﾞﾟ"));
                continue;
            }
            if ("ヴガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポ".indexOf(value.charAt(i)) != -1)
                tempResult.append(convertCharSpecial(String.valueOf(value.charAt(i)), "ヴガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポ",
                        "ｳﾞｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ"));
            else
                tempResult.append(value.charAt(i));
        }

        result = tempResult.toString();
        return result;
    }

    /**
     * <p>
     * 全角スペース -> 半角スペース処理。
     * </p>
     * 
     * @param value
     * @return
     */
    public static String toHalfSpace(final String value) {
        String result = "";
        final StringBuffer tempResult = new StringBuffer();
        if (value == null || "".equals(value))
            return value;
        for (int i = 0; i < value.length(); i++)
            if ("　".equals(String.valueOf(value.charAt(i))))
                tempResult.append(" ");
            else
                tempResult.append(value.charAt(i));

        result = tempResult.toString();
        return result;
    }

    /**
     * <p>
     * 半角-処理。
     * </p>
     * 
     * @param value
     * @return
     */
    public static String toHalfHaifun(final String value) {
        String result = "";
        final StringBuffer tempResult = new StringBuffer();
        if (value == null || "".equals(value))
            return value;
        for (int i = 0; i < value.length(); i++) {
            if ("－".indexOf(value.charAt(i)) != -1) {
                tempResult.append(convertCharNormal(String.valueOf(value.charAt(i)), "－", "-"));
            } else {
                tempResult.append(value.charAt(i));
            }
        }
        result = tempResult.toString();
        return result;
    }

    /**
     * <p>
     * 全角数字 -> 半角数字処理。
     * </p>
     * 
     * @param value
     * @return
     */
    public static String toHalfDigit(final String value) {
        String result = "";
        final StringBuffer tempResult = new StringBuffer();
        if (value == null || "".equals(value))
            return value;
        for (int i = 0; i < value.length(); i++) {
            if ("１２３４５６７８９０".indexOf(value.charAt(i)) != -1) {
                tempResult.append(convertCharNormal(String.valueOf(value.charAt(i)), "１２３４５６７８９０��O", "1234567890"));
            } else {
                tempResult.append(value.charAt(i));
            }
        }
        result = tempResult.toString();
        return result;
    }
    
    /**
     * <p>
     * 半角数字 -> 全角数字処理。
     * </p>
     * 
     * @param value
     * @return
     */
    public static String toFullDigit(final String value) {
        String result = "";
        final StringBuffer tempResult = new StringBuffer();
        if (value == null || "".equals(value))
            return value;
        for (int i = 0; i < value.length(); i++) {
            if ("1234567890".indexOf(value.charAt(i)) != -1) {
                tempResult.append(convertCharNormal(String.valueOf(value.charAt(i)),"1234567890", "１２３４５６７８９０��O"));
            } else {
                tempResult.append(value.charAt(i));
            }
        }
        result = tempResult.toString();
        return result;
    }

    private static String convertCharNormal(final String value, final String charFrom, final String charTo) {
        for (int i = 0; i < charFrom.length(); i++)
            if (String.valueOf(charFrom.charAt(i)).equals(value))
                return String.valueOf(charTo.charAt(i));

        return value;
    }

    private static String convertCharSpecial(final String value, final String charFrom, final String charTo) {
        final StringBuffer tempCharResult = new StringBuffer();
        String result = "";
        for (int i = 0; i < charFrom.length(); i++)
            if (String.valueOf(charFrom.charAt(i)).equals(value)) {
                tempCharResult.append(String.valueOf(charTo.charAt(i * 2)));
                tempCharResult.append(String.valueOf(charTo.charAt(i * 2 + 1)));
                result = tempCharResult.toString();
                return result;
            }

        return value;
    }

    /**
     * 
     * <p>
     * 処理。
     * </p>
     * 
     * @param request
     * @return
     */
    public static String readJSONString(HttpServletRequest request) {
        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
    }

    /**
     * <p>
     * 取得値のESCAPE処理。
     * </p>
     * 
     * @param word 取得値
     * @return String
     */
    public static String convertDBKeyWord(String word) {
        String result = word;

        if (isNullOrBlank(word)) {
            return result;
        }

        if (word.contains("_")) {
            result = result.replaceAll("_", "\\\\_");
        }

        if (word.contains("%")) {
            result = result.replaceAll("%", "\\\\%");
        }

        return result;
    }
    
    public static void main( String[] args ){
    	String add = "京都府京都市右京区山ノ内西裏";
		String add1 = StringUtil4Jp.getString(add, 32);
		add = add.length() > add1.length() ? add.substring(add1.length(), add.length()) : "";
		String add2 = StringUtil4Jp.getString(add, 32);
		add = add.length() > add2.length() ? add.substring(add2.length(), add.length()) : "";
		String add3 = StringUtil4Jp.getString(add, 32);
		
		System.out.println( add1 );
		System.out.println( add2 );
		System.out.println( add3 );
    }

}