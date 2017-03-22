/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: QuarzExecuteLog.java
 */

package com.core.scpwms.server.model.quartz;

import java.util.Date;

import com.core.db.server.model.Entity;

/**
 * <p>请添加注释</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/20<br/>
 */
public class QuartzExecuteLog extends Entity {
    private BaseQuartzSetting baseQuartzSetting;
    private Date insertDate;
    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    private String info6;
    private String info7;
    private String info8;
    private String info9;
    private String info10;

    public BaseQuartzSetting getBaseQuartzSetting() {
        return this.baseQuartzSetting;
    }

    public void setBaseQuartzSetting(BaseQuartzSetting baseQuartzSetting) {
        this.baseQuartzSetting = baseQuartzSetting;
    }

    public String getInfo1() {
        return this.info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return this.info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return this.info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return this.info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return this.info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInfo6() {
        return this.info6;
    }

    public void setInfo6(String info6) {
        this.info6 = info6;
    }

    public String getInfo7() {
        return this.info7;
    }

    public void setInfo7(String info7) {
        this.info7 = info7;
    }

    public String getInfo8() {
        return this.info8;
    }

    public void setInfo8(String info8) {
        this.info8 = info8;
    }

    public String getInfo9() {
        return this.info9;
    }

    public void setInfo9(String info9) {
        this.info9 = info9;
    }

    public String getInfo10() {
        return this.info10;
    }

    public void setInfo10(String info10) {
        this.info10 = info10;
    }

    public Date getInsertDate() {
        return this.insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public void setInfo(String[] infos) {
        if (infos == null || infos.length == 0)
            return;

        if (infos.length >= 1) {
            info1 = infos[0];
        }

        if (infos.length >= 2) {
            info2 = infos[1];
        }

        if (infos.length >= 3) {
            info3 = infos[2];
        }

        if (infos.length >= 4) {
            info4 = infos[3];
        }

        if (infos.length >= 5) {
            info5 = infos[4];
        }

        if (infos.length >= 6) {
            info6 = infos[5];
        }

        if (infos.length >= 7) {
            info7 = infos[6];
        }

        if (infos.length >= 8) {
            info8 = infos[7];
        }

        if (infos.length >= 9) {
            info9 = infos[8];
        }

        if (infos.length >= 10) {
            info10 = infos[9];
        }
    }
}
