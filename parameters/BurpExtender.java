package burp;
 
import java.io.PrintWriter;
import java.util.List;
 
public class BurpExtender implements IBurpExtender, IHttpListener{
 
    public PrintWriter stdout;
    public IExtensionHelpers helpers;
    private IBurpExtenderCallbacks callbacks;
 
    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks){
 
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("lansman");
        callbacks.registerHttpListener(this);
    }
 
    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest,
            IHttpRequestResponse messageInfo) {
        // 获取请求中的参数
        if(messageIsRequest){
            IRequestInfo iRequestInfo = helpers.analyzeRequest(messageInfo);
            // 获取请求中的所有参数
            List<IParameter> iParameters = iRequestInfo.getParameters();
            for (IParameter iParameter : iParameters) {
                if(iParameter.getType() == IParameter.PARAM_URL)
                    stdout.println("参数：" + iParameter.getName() + " 在 URL中");
                    stdout.println("参数：" + iParameter.getName() + " 的值为：" + iParameter.getValue());
            }
        }
 
    }
}
