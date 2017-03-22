package com.core.scpwms.server.model.warehouse;

import java.util.Set;

import com.core.business.model.security.User;
import com.core.scpwms.server.domain.DownloadTrackingEntity;

/**
 * 作业人员维护
 * 目前只有Code/name
 * 但是和BinGroup的关系、登录User的关系、Labor类型有待完善
 * 此维护画面可以暂缓
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class Labor extends DownloadTrackingEntity {

    /**
     * 人员代码
     */
    private String code;

    /**
     * 人员名称
     */
    private String name;

    /**
     * 用户
     */
    private User user;

    /**
     * 仓库
     */
    private Warehouse wh;

    /**
     * @See EnuLaborType
     */
    private String type;

    /** 描述 */
    private String description;

    /** 是否失效 */
    private Boolean disabled = Boolean.FALSE;

    private Set<LaborGroupLabor> groups;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Set<LaborGroupLabor> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<LaborGroupLabor> groups) {
        this.groups = groups;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
