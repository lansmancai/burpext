package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, IProxyListener {

    private PrintWriter stdout;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        callbacks.setExtensionName("lansman");
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.registerProxyListener(this);
    }

    @Override
    public void processProxyMessage(boolean messageIsRequest,
                                    IInterceptedProxyMessage message) {
        // 只操作请求消息
        if (messageIsRequest) {
            // 获取并打印出客户端 IP
            this.stdout.println(message.getClientIpAddress());
			this.stdout.println("what happen?");
            // Drop 掉所有请求
            message.setInterceptAction(IInterceptedProxyMessage.ACTION_DROP);
            // TODO here
        }
    }
}
