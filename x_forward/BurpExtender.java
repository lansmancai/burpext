package burp;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.net.URLEncoder;


public class BurpExtender implements IBurpExtender, IHttpListener
{
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;
    
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
    {
    	stdout = new PrintWriter(callbacks.getStdout(), true);
        this.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName("Random_X-forward-For");
        callbacks.registerHttpListener(this);
    }

    @Override
    public void processHttpMessage(int toolFlag,boolean messageIsRequest,IHttpRequestResponse messageInfo)
    {
    	try{
	    	if (toolFlag == 64 || toolFlag == 16 || toolFlag == 32 || toolFlag == 4){ 
	    		if (messageIsRequest){ 
	    			IRequestInfo analyzeRequest = helpers.analyzeRequest(messageInfo);
	    			String request = new String(messageInfo.getRequest());
	    			byte[] body = request.substring(analyzeRequest.getBodyOffset()).getBytes();
	    			List<String> headers = analyzeRequest.getHeaders();
	    			String xforward = "X-Forwarded-For: "+RandomIP.RandomIPstr();
	    			headers.add(xforward);
	    			stdout.println(xforward);
	    			byte[] new_Request = helpers.buildHttpMessage(headers,body);
	    			stdout.println(helpers.analyzeRequest(new_Request).getHeaders());
	    			messageInfo.setRequest(new_Request);//设置最终新的请求包
	    		}	    		
	    	}
    	}
    	catch(Exception e){
    		stdout.println(e);
    	}
    		
    }
}
