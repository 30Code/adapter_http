package cn.linhome.lib.adapter.http;

import android.text.TextUtils;
import android.util.Log;

import cn.linhome.lib.adapter.http.callback.FRequestCallback;
import cn.linhome.lib.adapter.http.handler.FRequestHandler;
import cn.linhome.lib.adapter.http.model.FRequestParams;
import cn.linhome.lib.adapter.http.model.FResponse;

import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.Map.Entry;

public abstract class FHttpUtil
{
    public static final String TAG = "SDHttpUtil";
    private static WeakHashMap<FRequestHandler, FHttpUtil.RequestInfo> sMapRequestInfo = new WeakHashMap();
    private static boolean sDebug;
    private static final FRequestCallback sDefaultRequestCallback = new FRequestCallback()
    {
        protected void onSuccess(FResponse resp)
        {
        }
    };

    public FHttpUtil()
    {
    }

    public static void setDebug(boolean debug)
    {
        sDebug = debug;
    }

    public final FRequestHandler post(FRequestParams params, FRequestCallback callback)
    {
        callback = this.checkCallback(params, callback);
        this.beforeImpl(params, callback);
        FRequestHandler requestHandler = this.postImpl(params, callback);
        this.afterImpl(callback, params, requestHandler);
        return requestHandler;
    }

    public final FRequestHandler get(FRequestParams params, FRequestCallback callback)
    {
        callback = this.checkCallback(params, callback);
        this.beforeImpl(params, callback);
        FRequestHandler requestHandler = this.getImpl(params, callback);
        this.afterImpl(callback, params, requestHandler);
        return requestHandler;
    }

    private void beforeImpl(FRequestParams params, FRequestCallback callback)
    {
        if (params != null && params.isNeedCancelSameRequest())
        {
            this.cancelSameRequest(params);
        }

    }

    private void cancelSameRequest(FRequestParams params)
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
                    Entry<FRequestHandler, FHttpUtil.RequestInfo> item = (Entry) it.next();
                    FRequestHandler requestHandler = (FRequestHandler) item.getKey();
                    FHttpUtil.RequestInfo requestInfo = (FHttpUtil.RequestInfo) item.getValue();
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
                    Entry<FRequestHandler, FHttpUtil.RequestInfo> item = (Entry) it.next();
                    FRequestHandler requestHandler = (FRequestHandler) item.getKey();
                    FHttpUtil.RequestInfo requestInfo = (FHttpUtil.RequestInfo) item.getValue();
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
                    Entry<FRequestHandler, FHttpUtil.RequestInfo> item = (Entry) it.next();
                    FRequestHandler requestHandler = (FRequestHandler) item.getKey();
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

    private void afterImpl(FRequestCallback callback, FRequestParams params, FRequestHandler requestHandler)
    {
        callback.setRequestHandler(requestHandler);
        if (params != null && requestHandler != null)
        {
            FHttpUtil.RequestInfo requestInfo = new FHttpUtil.RequestInfo();
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

    private FRequestCallback checkCallback(FRequestParams params, FRequestCallback callback)
    {
        if (callback == null)
        {
            callback = sDefaultRequestCallback;
        }

        callback.setRequestParams(params);
        return callback;
    }

    protected abstract FRequestHandler postImpl(FRequestParams var1, FRequestCallback var2);

    protected abstract FRequestHandler getImpl(FRequestParams var1, FRequestCallback var2);

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
