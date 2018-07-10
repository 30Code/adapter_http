package cn.linhome.lib.adapter.http.model;

import android.text.TextUtils;

import java.io.File;

public class SDFileBody
{
    private final File file;
    private final String fileName;
    private final String contentType;

    public SDFileBody(File file, String contentType)
    {
        this(file, contentType, (String) null);
    }

    public SDFileBody(File file, String contentType, String fileName)
    {
        this.file = file;
        if (TextUtils.isEmpty(contentType))
        {
            this.contentType = "application/octet-stream";
        } else
        {
            this.contentType = contentType;
        }

        this.fileName = fileName;
    }

    public File getFile()
    {
        return this.file;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public String getContentType()
    {
        return this.contentType;
    }
}

