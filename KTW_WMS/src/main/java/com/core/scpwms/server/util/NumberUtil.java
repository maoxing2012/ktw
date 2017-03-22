package com.core.scpwms.server.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数値関連をチェックするユーティリティクラス。
 * @author 邵華 2005/12/10
 * @version 1.0
 */
public abstract class NumberUtil {

    /**
     * str 引数で指定された内容が数値なら true を数値以外なら false を返す。
     * @param str 判定する文字列
     * @return 数値の判定結果
     */
    public static boolean checkInt(String str) {
        return StringUtil4Jp.checkHankakuDigit(str);
    }

    /**
     * num 引数の桁数が from、to 引数で指定された間にあるなら true を間にないなら false を返す。
     * @param from 最小桁数
     * @param to 最大桁数
     * @param num 判定する数値
     * @return 桁数の判定結果
     */
    public static boolean checkLength(int from, int to, long num) {
        if (num == 0) {
            return false;
        }
        return StringUtil4Jp.checkStringLength(from, to, StringUtil4Jp.toString(num));
    }

    /**
     * Sting 型の内容を short 型に変換する。
     * @param num 変換する String の値
     * @return 引数の数値表示
     */
    public static short toShort(String num) {
        return Short.parseShort(num);
    }

    /**
     * Sting 型の内容を int 型に変換する。
     * @param num 変換する String の値
     * @return 引数の数値表示
     */
    public static int toInt(String num) {
        return Integer.parseInt(num);
    }

    /**
     * Sting 型の内容を long 型に変換する。
     * @param num 変換する String の値
     * @return 引数の数値表示
     */
    public static long toLong(String num) {
        return Long.parseLong(num);
    }

    /**
     * Sting 型の内容を float 型に変換する。
     * @param num 変換する String の値
     * @return 引数の数値表示
     */
    public static float toFloat(String num) {
        return Float.parseFloat(num);
    }

    /**
     * Sting 型の内容を double 型に変換する。
     * 
     * @param num 変換する String の値
     * @return 引数の数値表示
     */
    public static double toDouble(String num) {
        return Double.parseDouble(num);
    }

    /**
     * 
     * <p>スケールが指定された値であり、値がこの BigDecimal と等しい BigDecimal を返します。</p>
     *
     * @param num
     * @param scale 返される BigDecimal の値のスケール 
     * @return スケールが指定された値であり、かつスケールなしの値が、この BigDecimal のスケールなしの値と、
     *          総体値を維持できる適当な 10 の累乗の積または商により決定される BigDecimal 
     */
    public static BigDecimal trucBigDecimal(BigDecimal num, int scale) {
        BigDecimal bd = num.setScale(scale, RoundingMode.DOWN);
        return bd;
    }
}