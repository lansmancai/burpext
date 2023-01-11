package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, IHttpListener{

    public PrintWriter stdout;
    public IExtensionHelpers helpers;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks){

        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("lansman");
        callbacks.registerHttpListener(this);
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest,
                                   IHttpRequestResponse messageInfo) {
        // 打印出请求的 Url 和 响应码
        if(messageIsRequest){
            stdout.println(helpers.bytesToString(messageInfo.getRequest()));
        }
        else{
            IResponseInfo responseInfo = helpers.analyzeResponse(messageInfo.getResponse());
            short statusCode = responseInfo.getStatusCode();
            stdout.printf("响应码 => %d\r\n", statusCode);
        }
    }
}
