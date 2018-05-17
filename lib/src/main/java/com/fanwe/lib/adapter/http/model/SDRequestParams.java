package com.fanwe.lib.adapter.http.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SDRequestParams
{
    private Map<String, Object> mData = new LinkedHashMap();
    private Map<String, SDFileBody> mDataFile = new LinkedHashMap();
    private List<SDMultiFile> mDataMultiFile = new ArrayList();
    private String url;
    private int mRequestDataType;
    private int mResponseDataType;
    private boolean mIsNeedProxy;
    private boolean mIsNeedCache;
    private boolean mIsNeedShowErrorMsg = true;
    private boolean mIsNeedCheckLoginState = true;
    private boolean mIsNeedCancelSameRequest = false;
    private String mCancelTag;

    public SDRequestParams()
    {
    }

    public SDRequestParams put(String key, Object value)
    {
        if (value != null)
        {
            this.mData.put(key, value);
        }

        return this;
    }

    public Object get(String key)
    {
        return this.mData.get(key);
    }

    public SDRequestParams putFile(String key, File file)
    {
        this.putFile(key, file, (String) null, (String) null);
        return this;
    }

    public SDRequestParams putFile(String key, File file, String contentType, String fileName)
    {
        this.mDataFile.put(key, new SDFileBody(file, contentType, fileName));
        return this;
    }

    public SDRequestParams putMultiFile(String key, File file)
    {
        this.putMultiFile(key, file, (String) null, (String) null);
        return this;
    }

    public SDRequestParams putMultiFile(String key, File file, String contentType, String fileName)
    {
        SDMultiFile multiFile = new SDMultiFile(key, new SDFileBody(file, contentType, fileName));
        this.mDataMultiFile.add(multiFile);
        return this;
    }

    public Map<String, Object> getData()
    {
        return this.mData;
    }

    public SDRequestParams setData(Map<String, Object> data)
    {
        if (data != null)
        {
            this.mData = data;
        }

        return this;
    }

    public Map<String, SDFileBody> getDataFile()
    {
        return this.mDataFile;
    }

    public SDRequestParams setDataFile(Map<String, SDFileBody> dataFile)
    {
        if (dataFile != null)
        {
            this.mDataFile = dataFile;
        }

        return this;
    }

    public List<SDMultiFile> getDataMultiFile()
    {
        return this.mDataMultiFile;
    }

    public SDRequestParams setDataMultiFile(List<SDMultiFile> dataMultiFile)
    {
        if (dataMultiFile != null)
        {
            this.mDataMultiFile = dataMultiFile;
        }

        return this;
    }

    public int getRequestDataType()
    {
        return this.mRequestDataType;
    }

    public SDRequestParams setRequestDataType(int requestDataType)
    {
        this.mRequestDataType = requestDataType;
        return this;
    }

    public int getResponseDataType()
    {
        return this.mResponseDataType;
    }

    public SDRequestParams setResponseDataType(int responseDataType)
    {
        this.mResponseDataType = responseDataType;
        return this;
    }

    public String getUrl()
    {
        return this.url;
    }

    public SDRequestParams setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public boolean isNeedProxy()
    {
        return this.mIsNeedProxy;
    }

    public SDRequestParams setNeedProxy(boolean isNeedProxy)
    {
        this.mIsNeedProxy = isNeedProxy;
        return this;
    }

    public boolean isNeedCache()
    {
        return this.mIsNeedCache;
    }

    public SDRequestParams setNeedCache(boolean isNeedCache)
    {
        this.mIsNeedCache = isNeedCache;
        return this;
    }

    public boolean isNeedShowErrorMsg()
    {
        return this.mIsNeedShowErrorMsg;
    }

    public SDRequestParams setNeedShowErrorMsg(boolean isNeedShowErrorMsg)
    {
        this.mIsNeedShowErrorMsg = isNeedShowErrorMsg;
        return this;
    }

    public boolean isNeedCancelSameRequest()
    {
        return this.mIsNeedCancelSameRequest;
    }

    public SDRequestParams setNeedCancelSameRequest(boolean needCancelSameRequest)
    {
        this.mIsNeedCancelSameRequest = needCancelSameRequest;
        return this;
    }

    public SDRequestParams putCtl(String ctl)
    {
        return this.put("ctl", ctl);
    }

    public Object getCtl()
    {
        return this.get("ctl");
    }

    public SDRequestParams putAct(String act)
    {
        return this.put("act", act);
    }

    public Object getAct()
    {
        return this.get("act");
    }

    public String getActInfo()
    {
        return "ctl=" + this.getCtl() + "," + "act=" + this.getAct();
    }

    public String getCancelTag()
    {
        return this.mCancelTag;
    }

    public void setCancelTag(String cancelTag)
    {
        this.mCancelTag = cancelTag;
    }

    public boolean isNeedCheckLoginState()
    {
        return this.mIsNeedCheckLoginState;
    }

    public SDRequestParams setNeedCheckLoginState(boolean isNeedCheckLoginState)
    {
        this.mIsNeedCheckLoginState = isNeedCheckLoginState;
        return this;
    }

    public String parseToUrl()
    {
        StringBuilder sb = new StringBuilder();
        if (this.url != null)
        {
            sb.append(this.url);
        }

        if (this.mData != null && !this.mData.isEmpty())
        {
            int i = 0;

            for (Iterator var3 = this.mData.entrySet().iterator(); var3.hasNext(); ++i)
            {
                Entry<String, Object> item = (Entry) var3.next();
                if (i == 0)
                {
                    sb.append("?");
                }

                sb.append((String) item.getKey()).append("=").append(item.getValue()).append("&");
            }

            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if (this.mData != null && !this.mData.isEmpty())
        {
            Iterator var2 = this.mData.entrySet().iterator();

            while (var2.hasNext())
            {
                Entry<String, Object> item = (Entry) var2.next();
                sb.append((String) item.getKey()).append("=").append(String.valueOf(item.getValue())).append(",");
            }
        } else
        {
            sb.append(super.toString());
        }

        return sb.toString();
    }
}
