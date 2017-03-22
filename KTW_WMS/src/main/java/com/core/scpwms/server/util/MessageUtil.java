package com.core.scpwms.server.util;

import java.util.List;

/**
 * 
 * <p>处理详细消息</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/17<br/>
 */
@SuppressWarnings("all")
public class MessageUtil {

    private static final String TABLE = "<TABLE>";

    private static final String TABLE_END = "</TABLE>";

    private static final String TR = "<TR>";

    private static final String TR_END = "</TR>";

    private static final String TD = "<TD>";

    private static final String TD_END = "</TD>";

    /**
     * 
     * <p>国际化，加入HTML标签</p>
     *
     * @param detailInfos
     * @return
     */
    public static final String formatDetailMes(List<String[]> detailInfos) {
        StringBuffer sb = new StringBuffer("");

        if (detailInfos != null && detailInfos.size() > 0) {
            sb.append(TABLE);
            for (String[] row : detailInfos) {
                sb.append(TR);
                for (String column : row) {
                    sb.append(TD);
                    sb.append(LocaleUtil.getText(column));
                    sb.append(TD_END);
                }
                sb.append(TR_END);
            }
            sb.append(TABLE_END);
        }

        return sb.toString();
    }
}
