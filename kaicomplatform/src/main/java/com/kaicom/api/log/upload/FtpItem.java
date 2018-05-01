package com.kaicom.api.log.upload;

public class FtpItem {

    private String serverFileName;
    private String localFile;
    private String remotePath;

    public String getServerFileName() {
        return serverFileName;
    }
    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }
    public String getLocalFile() {
        return localFile;
    }
    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getRemotePath() {
        return remotePath;
    }
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }
    @Override
    public String toString() {
        return "FtpItem [serverFileName=" + serverFileName + ", localFile="
                + localFile + "]";
    }
    
}
