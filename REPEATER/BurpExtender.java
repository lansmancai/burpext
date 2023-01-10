package burp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        callbacks.setExtensionName("lansman");
        callbacks.registerContextMenuFactory(this);
    }


    @Override
    public List<JMenuItem> createMenuItems(final IContextMenuInvocation invocation) {

        List<JMenuItem> listMenuItems = new ArrayList<JMenuItem>();
        // 菜单只在 REPEATER 工具的右键菜单中显示
        if (invocation.getToolFlag() == IBurpExtenderCallbacks.TOOL_REPEATER) {
            //子菜单
            JMenuItem menuItem;
            menuItem = new JMenuItem("子菜单测试");

            //父级菜单
            JMenu jMenu = new JMenu("lansman");
            jMenu.add(menuItem);
            listMenuItems.add(jMenu);
        }
        return listMenuItems;
    }
}
