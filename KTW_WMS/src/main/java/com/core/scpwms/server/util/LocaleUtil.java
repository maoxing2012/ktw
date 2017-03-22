/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: LocaleUtil.java
 */

package com.core.scpwms.server.util;

import com.core.business.web.security.UserHolder;
import com.core.db.util.LocalizedMessage;

/**
 * <p>取得国际化信息</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/30<br/>
 */
public class LocaleUtil {

    /**
     * 
     * <p>适用于画面调用，UserHolder非空</p>
     * @param message
     *
     * @return
     */
    public static String getText(String message) {
        return LocalizedMessage.getLocalizedMessage(message, UserHolder.getReferenceModel(), UserHolder.getLanguage());
    }

    /**
     * 
     * <p>适用于Batch等调用，UserHolder为空的情况</p>
     *
     * @param message
     * @param refMode
     * @param locale
     * @return
     */
    public static String getText(String message, String refMode, String locale) {
        return LocalizedMessage.getLocalizedMessage(message, refMode, locale);
    }
}
