package com.core.scpwms.server.service.edi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.WSSecurityException;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;

public class SoapInHandler extends AbstractHandler implements CallbackHandler {
	
	private static final Map<String,String> pwMockDB = new HashMap<String,String>();
	static{
		pwMockDB.put("mbp", "mbpsoft");
		pwMockDB.put("mbp-01", "mbpsoft-01");
		pwMockDB.put("mbp-02", "mbpsoft-02");
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];
		String id = callback.getIdentifier();
		String validPs = pwMockDB.get(id);
		
		if( WSConstants.PASSWORD_TEXT.equals(callback.getPasswordType()) ){
			String pw = callback.getPassword();
			if( pw == null || !pw.equalsIgnoreCase(validPs) ){
				throw new WSSecurityException(" Password not match ");
			}
		}else{
			callback.setPassword(validPs);
		}
	}

	@Override
	public void invoke(MessageContext arg0) throws Exception {
		// Nothing
	}

}