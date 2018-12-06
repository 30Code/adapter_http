package cn.linhome.lib.adapter.http.model;

public class FMultiFile
{
    public final String key;
    public final FFileBody fileBody;

    public FMultiFile(String key, FFileBody fileBody)
    {
        this.key = key;
        this.fileBody = fileBody;
    }

    public String getKey()
    {
        return this.key;
    }

    public FFileBody getFileBody()
    {
        return this.fileBody;
    }
}