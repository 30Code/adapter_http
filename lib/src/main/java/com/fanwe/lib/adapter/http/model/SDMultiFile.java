package com.fanwe.lib.adapter.http.model;

public class SDMultiFile
{
    public final String key;
    public final SDFileBody fileBody;

    public SDMultiFile(String key, SDFileBody fileBody)
    {
        this.key = key;
        this.fileBody = fileBody;
    }

    public String getKey()
    {
        return this.key;
    }

    public SDFileBody getFileBody()
    {
        return this.fileBody;
    }
}