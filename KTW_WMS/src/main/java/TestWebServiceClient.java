
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.core.scpwms.server.mobile.MobileServiceManager;
import com.core.scpwms.server.mobile.bean.MobileRequest;
import com.core.scpwms.server.util.JsonHelper;

public class TestWebServiceClient {

	public static void main(String[] args) {
		String wsURL = "http://localhost:9097/scpwms/webService/MobileServiceManager";
		try {
			Service serviceModel = new ObjectServiceFactory().create(MobileServiceManager.class);
			MobileServiceManager client = (MobileServiceManager) new XFireProxyFactory() .create(serviceModel, wsURL);
			
			MobileRequest request = new MobileRequest();
			request.setUserId(200004L);
			request.setWhId(1000L);
			request.setOwnerId(1000L);
			request.getParameters().put("invId", 5818L);
			
			String requestStr = JsonHelper.toJsonString(request, new String[]{"parameters"});
			System.out.println(requestStr);
			String result = client.getInvDetailInfo(requestStr);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
