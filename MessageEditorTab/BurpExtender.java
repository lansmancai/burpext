
package burp;
 
import java.awt.Component;
import java.io.PrintWriter;
 
public class BurpExtender implements IBurpExtender, IMessageEditorTabFactory{
 
    public PrintWriter stdout;
    public IExtensionHelpers helpers;
    private IBurpExtenderCallbacks callbacks;
 
    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks){
 
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("lansman");
        callbacks.registerMessageEditorTabFactory(this);
    }
 
    @Override
    public IMessageEditorTab createNewInstance(
            IMessageEditorController controller, boolean editable) {
        // 返回 IMessageEditorTab 的实例
        return new iMessageEditorTab();
    }
 
    class iMessageEditorTab implements IMessageEditorTab{
 
        // 创建一个新的文本编辑器
        private ITextEditor iTextEditor = callbacks.createTextEditor();
 
        @Override
        public String getTabCaption() {
            // 设置消息编辑器标签页的标题
            return "测试 MessageEditorTab";
        }
 
        @Override
        public Component getUiComponent() {
            // 返回 iTextEditor 的组件信息，当然也可以放置其他的组件
            return iTextEditor.getComponent();
        }
 
        @Override
        public boolean isEnabled(byte[] content, boolean isRequest) {
            // 在显示一个新的 HTTP 消息时，启用自定义的标签页
            // 通过 content 和 isRequest 也可以对特定的消息进行设置
            return true;
        }
 
        @Override
        public void setMessage(byte[] content, boolean isRequest) {
            // 把请求消息里面的 data 参数进行 Base64 编码操作
            // 这里并未处理参数中没有 data 时的异常
            IParameter parameter = helpers.getRequestParameter(content, "id");
            stdout.println("id = " + parameter.getValue());
            iTextEditor.setText(helpers.stringToBytes(helpers.base64Encode(parameter.getValue())));
        }
 
        @Override
        public byte[] getMessage() {
            // 获取 iTextEditor 的文本
            return iTextEditor.getText();
        }
 
        @Override
        public boolean isModified() {
            // 允许用户修改当前的消息
            return true;
        }
 
        @Override
        public byte[] getSelectedData() {
            // 直接返回 iTextEditor 中选中的文本
            return iTextEditor.getSelectedText();
        }
 
    }
}
