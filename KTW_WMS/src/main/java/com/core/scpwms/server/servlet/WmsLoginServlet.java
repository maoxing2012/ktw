package com.core.scpwms.server.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.business.exception.AuthenticationException;
import com.core.business.exception.WareHouseNotFoundException;
import com.core.business.model.security.User;
import com.core.business.service.security.LayoutFilter;
import com.core.business.service.security.SecurityFilter;
import com.core.business.servlet.LoginServlet;
import com.core.db.util.Constant;
import com.core.db.util.LocalizedMessage;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.warehouse.WarehouseManager;

/**
 * WMS登陆处理 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class WmsLoginServlet extends LoginServlet {
	
	private static final long serialVersionUID = 5580009056578670768L;
	//提示信息 
	public String message;
	//密码有效期
	public Long oldTime;
	//密码预警期
	public Integer preAlertDay = 0;
	
	
	protected void doPost(final HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		final String loginName = req.getParameter("login_name");
		final String password = req.getParameter("password");
		
		final String referenceModel = "plusone";
		final String locale = req.getParameter("locale");		
		
		//用户信息验证
		User loginUser = null;
		try {
			loginUser = authenticateUser(loginName,password, referenceModel,locale);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			String hintMsg = LocalizedMessage.getLocalizedMessage(e.getClass().getSimpleName(), referenceModel, locale);
			res.getOutputStream().write(hintMsg.getBytes("UTF-8"));
			return;
		}
		
		SecurityFilter sf = buildSecurityFilter(loginUser, referenceModel, locale, "wms");
		
		HttpSession session = req.getSession(true);
		session.setAttribute(Constant.SECURITY_FILTER_IN_SESSION, sf);//权限过滤器
		session.setAttribute(Constant.USER_IN_SESSION, loginUser);//当前用户
		session.setAttribute(Constant.MODELTYPE, referenceModel);//当前参考模型
		session.setAttribute(Constant.LOCALE, locale);//当前语言环境		
		session.setAttribute(Constant.MODULE, "wms");
		
		LayoutFilter customLayout = getCustomLayout(loginUser);
		req.getSession().setAttribute(Constant.LAYOUT_FILTER_IN_SESSION, customLayout);
		
		
		//仓库权限验证
		try{
			Warehouse wh = queryWarehouse(loginUser);
			session.setAttribute(WmsConstant.SESSION_WAREHOUSE, wh);//当前的仓库
			session.setAttribute(WmsConstant.SESSION_WAREHOUSE_ID, wh.getId());//当前的仓库	
			
		}catch(AuthenticationException ex){
			ex.printStackTrace();
			String hintMsg = LocalizedMessage.getLocalizedMessage(ex.getClass().getSimpleName(), referenceModel, locale);
			res.getOutputStream().write(hintMsg.getBytes("UTF-8"));
			return;
		}
		
		//密码过期验证
		oldTime = loginUser.getPasswordExpiryDate() == null ? null : loginUser.getPasswordExpiryDate().getTime();
		if (oldTime == null || preAlertDay == 0) {
			res.getOutputStream().write("success".getBytes());
		} else {
			long nowTime = new Date().getTime();
			long vs = (oldTime - nowTime) / (24 * 60 * 60 * 1000);  
			if (vs <= preAlertDay) {
				res.getOutputStream().write(("successAndError" + (vs + 1)).getBytes());
			} else {
				res.getOutputStream().write("success".getBytes());
			}
		}
	}

	/**
	 * 仓库信息取得
	 * @param loginName
	 * @return
	 * @throws AuthenticationException
	 */
	private Warehouse queryWarehouse(User loginUser) throws AuthenticationException{
		WarehouseManager warehouseManager = (WarehouseManager) ac.getBean("warehouseManager");
		Warehouse wh = warehouseManager.getWarehouseByLoginUser(loginUser);
		if (wh == null) {
			throw new WareHouseNotFoundException("No warehouse avaliable for user.");
		}
		return wh ;
	}
}