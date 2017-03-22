package com.core.scpwms.server.util;

import java.math.BigDecimal;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.model.common.PackageDetail;

/**
 * 库存单位工具类
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public class PrecisionUtils {

    /**
     * 根据包装单位上的库存精度，进行小数点处理
     * 超出部分按照四舍五入处理
     * @param o
     * @param precision
     * @return
     */
    private static double formatByPackage(Object o, Long precision) {

        int pre = 2;

        if (precision != null) {
            pre = precision.intValue();
        }

        double pDouble = Double.parseDouble(o.toString());
        BigDecimal bd = new BigDecimal(pDouble);
        BigDecimal bdWithPrecision = bd.setScale(pre, BigDecimal.ROUND_HALF_UP);
        pDouble = bdWithPrecision.doubleValue();

        return pDouble;
    }

    /**
     * 
     * <p>按照基本包装上的库存精度，调整数量的小数点位数</p>
     *
     * @param qty
     * @param pack
     * @return
     */
    public static double formatByBasePackage(Double qty, PackageDetail pack) {
        return formatByPackage(qty, pack.getPackageInfo().getP1000());
    }

    /**
     * 
     * <p>按照包装上的库存精度，调整数量的小数点位数</p>
     *
     * @param qty
     * @param pack
     * @return
     */
    public static double formatByPackage(Double qty, PackageDetail pack) {
        return formatByPackage(qty, pack.getPrecision());
    }

    /**
     * 
     * <p>用于基本包装(EA)数量之间的比较</p>
     *
     * @param source
     * @param target
     * @param pack
     * @return
     */
    public static int compareByBasePackage(Double source, Double target, PackageDetail pack) {
        return compareByPacakge(source, target, pack.getPackageInfo().getP1000().getPrecision());

    }

    /**
     * 
     * <p>用于包装数量之间的比较</p>
     *
     * @param source
     * @param target
     * @param pack
     * @return
     */
    public static int compareByPackage(Double source, Double target, PackageDetail pack) {
        return compareByPacakge(source, target, pack.getPrecision());
    }

    /**
     * 
     * <p>经过库存精度调整以后，比较大小</p>
     *
     * @param source
     * @param target
     * @param precision
     * @return
     */
    private static int compareByPacakge(Double source, Double target, Long precision) {

        int pre = 2;

        if (precision != null) {
            pre = precision.intValue();
        }

        BigDecimal src = (new BigDecimal(source.toString())).setScale(pre, BigDecimal.ROUND_HALF_UP);
        BigDecimal dst = (new BigDecimal(target.toString())).setScale(pre, BigDecimal.ROUND_HALF_UP);

        return src.compareTo(dst);
    }

    /**
     * 
     * <p>包装数转基本包装数，并按库存精度做小数处理</p>
     *
     * @param packQty
     * @param pack
     * @param basePack
     * @return
     */
    public static double getBaseQty(double packQty, PackageDetail pack) {
        Double resultQty = DoubleUtil.mul(new Double(packQty), pack.getCoefficient());
        return formatByPackage(resultQty, pack.getPackageInfo().getP1000().getPrecision());
    }

    /**
     * 
     * <p>基本包装数转包装数，并按照库存精度做小数处理</p>
     *
     * @param baseQty
     * @param pack
     * @param basePack
     * @return
     */
    public static double getPackQty(double baseQty, PackageDetail pack) {
    	if( pack == null )
    		return 0;
    	
    	if( pack.getCoefficient() == null || pack.getCoefficient() <= 0D )
    		return 0;
    	
        if (pack.getPrecision().longValue() == 0) {
            // 向上取整，得到整包装数量
            return Math.ceil(baseQty / pack.getCoefficient().doubleValue());
        }

        // 根据包装精度计算包装数量
        return DoubleUtil.div(new Double(baseQty), pack.getCoefficient(), new Integer(pack.getPrecision().intValue())).doubleValue();

    }
}
