package burp;
 
import java.io.PrintWriter;
 
public class BurpExtender implements IBurpExtender, IHttpListener {

    private PrintWriter stdout;
    public IBurpExtenderCallbacks iCallbacks;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {

        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.setExtensionName("lansman");
        callbacks.registerHttpListener(this);
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest,
                                   IHttpRequestResponse messageInfo) {

        IHttpService iHttpService = messageInfo.getHttpService();

        this.stdout.println(iHttpService.getHost());
        this.stdout.println(iHttpService.getPort());
        this.stdout.println(iHttpService.getProtocol());
    }
}
