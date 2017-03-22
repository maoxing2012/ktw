import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


public class KTW_WMS_START {
	protected int port = 9097;  
	  
    protected String webapp = "ktw-2.0";  
  
    protected String deployPath = "./target/" + webapp;  
  
    public static void main(String[] args) throws Exception {  
    	KTW_WMS_START jetty = new KTW_WMS_START();  
        jetty.init();  
        jetty.run();  
    }  
  
    protected void init() {  
          
    }  
  
    protected void run() throws Exception {  
        Server server = new Server(this.port);  
          
        WebAppContext context = new WebAppContext();  
        
        context.setDescriptor(this.webapp + "/WEB-INF/web.xml");  
        context.setResourceBase(deployPath);  
        context.setContextPath("/");  
        context.setParentLoaderPriority(true);  
          
        server.setHandler(context);  
          
        server.start();  
        server.join();  
    }  
}
