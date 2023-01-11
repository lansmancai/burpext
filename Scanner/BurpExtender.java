package burp;

import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, IScannerListener{

    public PrintWriter stdout;
    public IExtensionHelpers helpers;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks){

        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("lansman");
        callbacks.registerScannerListener(this);
    }

    @Override
    public void newScanIssue(IScanIssue issue) {
        // TODO Auto-generated method stub
        stdout.println("扫描到新的问题 :");
        stdout.println("url => " + issue.getUrl());
        stdout.println("详情 => " + issue.getIssueDetail());
    }
}
