package burp;
import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, IExtensionStateListener{

    private PrintWriter stdout;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks){

        this.stdout = new PrintWriter(callbacks.getStdout(), true);

        callbacks.setExtensionName("lansman");
        // 先注册扩展状态监听器
        callbacks.registerExtensionStateListener(this);
    }

    // 重写 extensionUnloaded 方法
    @Override
    public void extensionUnloaded() {
        // TODO
        this.stdout.println("extensionUnloaded ...");
    }
}
