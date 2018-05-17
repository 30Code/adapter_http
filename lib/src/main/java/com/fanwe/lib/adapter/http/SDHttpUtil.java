package com.fanwe.lib.adapter.http;

import android.text.TextUtils;
import android.util.Log;

import com.fanwe.lib.adapter.http.callback.SDRequestCallback;
import com.fanwe.lib.adapter.http.handler.SDRequestHandler;
import com.fanwe.lib.adapter.http.model.SDRequestParams;
import com.fanwe.lib.adapter.http.model.SDResponse;

import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.Map.Entry;

public abstract class SDHttpUtil
{
    public static final String TAG = "SDHttpUtil";
    private static WeakHashMap<SDRequestHandler, SDHttpUtil.RequestInfo> sMapRequestInfo = new WeakHashMap();
    private static boolean sDebug;
    private static final SDRequestCallback sDefaultRequestCallback = new SDRequestCallback()
    {
        protected void onSuccess(SDResponse resp)
        {
        }
    };

    public SDHttpUtil()
    {
    }

    public static void setDebug(boolean debug)
    {
        sDebug = debug;
    }

    public final SDRequestHandler post(SDRequestParams params, SDRequestCallback callback)
    {
        callback = this.checkCallback(params, callback);
        this.beforeImpl(params, callback);
        SDRequestHandler requestHandler = this.postImpl(params, callback);
        this.afterImpl(callback, params, requestHandler);
        return requestHandler;
    }

    public final SDRequestHandler get(SDRequestParams params, SDRequestCallback callback)
    {
        callback = this.checkCallback(params, callback);
        this.beforeImpl(params, callback);
        SDRequestHandler requestHandler = this.getImpl(params, callback);
        this.afterImpl(callback, params, requestHandler);
        return requestHandler;
    }

    private void beforeImpl(SDRequestParams params, SDRequestCallback callback)
    {
        if (params != null && params.isNeedCancelSameRequest())
        {
            this.cancelSameRequest(params);
        }

    }

    private void cancelSameRequest(SDRequestParams params)
    {
        String actInfo = params.getActInfo();
        if (!TextUtils.isEmpty(actInfo))
        {
            WeakHashMap var3 = sMapRequestInfo;
            synchronized (sMapRequestInfo)
            {
                Iterator it = sMapRequestInfo.entrySet().iterator();

                while (it.hasNext())
                {
                    Entry<SDRequestHandler, SDHttpUtil.RequestInfo> item = (Entry) it.next();
                    SDRequestHandler requestHandler = (SDRequestHandler) item.getKey();
                    SDHttpUtil.RequestInfo requestInfo = (SDHttpUtil.RequestInfo) item.getValue();
                    if (actInfo.equals(requestInfo.actInfo))
                    {
                        if (requestHandler != null)
                        {
                            requestHandler.cancel();
                        }

                        it.remove();
                        if (sDebug)
                        {
                            Log.i("SDHttpUtil", "cancelSameRequest:" + actInfo);
                        }
                    }
                }

            }
        }
    }

    public void cancelRequest(String cancelTag)
    {
        if (cancelTag != null)
        {
            WeakHashMap var2 = sMapRequestInfo;
            synchronized (sMapRequestInfo)
            {
                Iterator it = sMapRequestInfo.entrySet().iterator();

                while (it.hasNext())
                {
                    Entry<SDRequestHandler, SDHttpUtil.RequestInfo> item = (Entry) it.next();
                    SDRequestHandler requestHandler = (SDRequestHandler) item.getKey();
                    SDHttpUtil.RequestInfo requestInfo = (SDHttpUtil.RequestInfo) item.getValue();
                    if (cancelTag.equals(requestInfo.cancelTag))
                    {
                        if (requestHandler != null)
                        {
                            requestHandler.cancel();
                        }

                        it.remove();
                        if (sDebug)
                        {
                            Log.i("SDHttpUtil", "cancelRequest:" + requestInfo.toString());
                        }
                    }
                }

            }
        }
    }

    public void cancelAllRequest()
    {
        WeakHashMap var1 = sMapRequestInfo;
        synchronized (sMapRequestInfo)
        {
            if (!sMapRequestInfo.isEmpty())
            {
                Iterator it = sMapRequestInfo.entrySet().iterator();

                while (it.hasNext())
                {
                    Entry<SDRequestHandler, SDHttpUtil.RequestInfo> item = (Entry) it.next();
                    SDRequestHandler requestHandler = (SDRequestHandler) item.getKey();
                    if (requestHandler != null)
                    {
                        requestHandler.cancel();
                    }

                    it.remove();
                    if (sDebug)
                    {
                        Log.i("SDHttpUtil", "cancelRequest:" + item.getValue());
                    }
                }
            }

        }
    }

    private void afterImpl(SDRequestCallback callback, SDRequestParams params, SDRequestHandler requestHandler)
    {
        callback.setRequestHandler(requestHandler);
        if (params != null && requestHandler != null)
        {
            SDHttpUtil.RequestInfo requestInfo = new SDHttpUtil.RequestInfo();
            requestInfo.actInfo = params.getActInfo();
            String cancelTag = callback.getCancelTag();
            if (TextUtils.isEmpty(cancelTag))
            {
                cancelTag = params.getCancelTag();
            }

            requestInfo.cancelTag = cancelTag;
            sMapRequestInfo.put(requestHandler, requestInfo);
            if (sDebug)
            {
                Log.i("SDHttpUtil", "request(" + requestInfo.toString() + ") size:" + sMapRequestInfo.size());
            }
        }

    }

    private SDRequestCallback checkCallback(SDRequestParams params, SDRequestCallback callback)
    {
        if (callback == null)
        {
            callback = sDefaultRequestCallback;
        }

        callback.setRequestParams(params);
        return callback;
    }

    protected abstract SDRequestHandler postImpl(SDRequestParams var1, SDRequestCallback var2);

    protected abstract SDRequestHandler getImpl(SDRequestParams var1, SDRequestCallback var2);

    private static class RequestInfo
    {
        public String actInfo;
        public Object cancelTag;

        private RequestInfo()
        {
        }

        public String toString()
        {
            StringBuilder sb = (new StringBuilder()).append("actInfo:").append(this.actInfo).append(",").append("cancelTag:").append(this.cancelTag);
            return sb.toString();
        }
    }
}
