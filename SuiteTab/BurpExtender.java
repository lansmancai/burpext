package burp;

import java.awt.Component;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BurpExtender implements IBurpExtender, ITab{

    public PrintWriter stdout;
    public IExtensionHelpers helpers;

    private JPanel jPanel1;
    private JButton jButton1;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks){

        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("lansman");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //创建一个 JPanel
                jPanel1 = new JPanel();
                jButton1 = new JButton("点我");

                // 将按钮添加到面板中
                jPanel1.add(jButton1);

                //自定义的 UI 组件
                callbacks.customizeUiComponent(jPanel1);
                //将自定义的标签页添加到Burp UI 中
                callbacks.addSuiteTab(BurpExtender.this);
            }
        });
    }

    @Override
    public String getTabCaption() {
        // 返回自定义标签页的标题
        return "lansman";
    }

    @Override
    public Component getUiComponent() {
        // 返回自定义标签页中的面板的组件对象
        return jPanel1;
    }
}
