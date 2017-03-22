package com.core.scpwms.server.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.core.business.action.ConfigAction;
import com.core.business.security.acegi.holder.SecurityContextHolder;
import com.core.db.util.Constant;
import com.core.scpwms.client.rpc.CustomService;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.warehouse.WarehouseManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SwitchWarehouseServlet extends RemoteServiceServlet implements CustomService{

	 protected ApplicationContext ac;
	 
	 public void init(ServletConfig sc) throws ServletException {
		super.init(sc);
		ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc.getServletContext());
	}

	public Map<String, Object> swichWarehouse(Long warehouseId) throws Exception {
		Long wId = Long.valueOf(warehouseId);
		if(isSameWarehouse(wId,this.getThreadLocalRequest())){
			throw new Exception("未切换仓库！");
		}
		Object sessionWH = this.getThreadLocalRequest().getSession(false).getAttribute("SESSION_WAREHOUSE");
		if(sessionWH == null){
			throw new Exception("session错误！");
		}
		
		WarehouseManager wm = (WarehouseManager)ac.getBean("warehouseManager");
		Warehouse warehouse = wm.load(Warehouse.class,wId);
		if(warehouse == null){
			throw new Exception("所选仓库错误！");
		}
		
		
		this.getThreadLocalRequest().getSession(false).removeAttribute(WmsConstant.SESSION_WAREHOUSE);
		this.getThreadLocalRequest().getSession(false).setAttribute(WmsConstant.SESSION_WAREHOUSE,warehouse);
		this.getThreadLocalRequest().getSession(false).removeAttribute(WmsConstant.SESSION_WAREHOUSE_ID);
		this.getThreadLocalRequest().getSession(false).setAttribute(WmsConstant.SESSION_WAREHOUSE_ID,warehouse.getId());
		
		
		
		SecurityContextHolder.clearAllSession();
		SecurityContextHolder.addSecurityContext(this.getThreadLocalRequest().getSession(false));
		
		//重新初始化全局参数
		ConfigAction configAction = (ConfigAction) ac.getBean("configAction");
		configAction.setReferenceModel(this.getThreadLocalRequest().getSession().getAttribute(Constant.MODELTYPE).toString());
		Map<String,Object> results = new HashMap<String,Object>();
		Map globalMap = configAction.reInitGlobalParams(new HashMap());
		results.put("globals",globalMap);
		results.put("warehouseName",warehouse.getName());
		return results;
	}
	 
	private boolean isSameWarehouse(Long id,HttpServletRequest req){
		Warehouse w = (Warehouse)req.getSession(false).getAttribute(WmsConstant.SESSION_WAREHOUSE);
		Assert.notNull(w,"switch warehouse id =v"+id);
		if(w.getId() == id){
			return true;
		}else{
			return false;
		}
	}
}

