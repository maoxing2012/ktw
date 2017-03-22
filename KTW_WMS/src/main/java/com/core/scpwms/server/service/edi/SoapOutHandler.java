package com.core.scpwms.server.service.edi;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.codehaus.xfire.transport.http.XFireServletController;
import org.codehaus.xfire.util.dom.DOMInHandler;
import org.codehaus.xfire.util.dom.DOMOutHandler;
import org.dom4j.io.DOMReader;
import org.w3c.dom.Document;

import com.core.scpwms.server.enumerate.EnuSoapInfoType;
import com.core.scpwms.server.model.edi.SoapInfo;
import com.core.scpwms.server.util.AddressUtil;

public class SoapOutHandler extends AbstractHandler {
	private EdiManager ediManager;
	
    public EdiManager getEdiManager() {
		return this.ediManager;
	}

	public void setEdiManager(EdiManager ediManager) {
		this.ediManager = ediManager;
	}

	public void invoke(MessageContext arg0) throws Exception { 
		// Request Soap Info
		Document inputDoc = (Document) arg0.getInMessage().getProperty(DOMInHandler.DOM_MESSAGE); 
		Document outputDoc = (Document) arg0.getOutMessage().getProperty(DOMOutHandler.DOM_MESSAGE); 

        SoapInfo requestSoapInfo = new SoapInfo();
        requestSoapInfo.setXmlContent(buildDocment(inputDoc).asXML());
        requestSoapInfo.setType(EnuSoapInfoType.REQUEST_XML);
        HttpServletRequest httpServlet = XFireServletController.getRequest();
        String ipAddr = AddressUtil.getIpAddr(httpServlet);
        requestSoapInfo.setAccessIp(ipAddr);
        
        Long id = ediManager.save(requestSoapInfo);
		
		// Response Soap Info
        
        SoapInfo responseSoapInfo = new SoapInfo();
        responseSoapInfo.setXmlContent(buildDocment(outputDoc).asXML());
        responseSoapInfo.setType(EnuSoapInfoType.RESPONSE_XML);
        responseSoapInfo.setAccessIp(ipAddr);
        responseSoapInfo.setRelatedSoapInfoId(id);
        
        ediManager.save(responseSoapInfo);
	} 

    /** converts a W3C DOM document into a dom4j document */ 
    public org.dom4j.Document buildDocment(org.w3c.dom.Document domDocument) { 
        DOMReader xmlReader = new DOMReader(); 
        return xmlReader.read(domDocument); 
    } 

}