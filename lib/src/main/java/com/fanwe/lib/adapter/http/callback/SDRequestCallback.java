package com.fanwe.lib.adapter.http.callback;

import android.os.Handler;
import android.os.Looper;

import com.fanwe.lib.adapter.http.handler.SDRequestHandler;
import com.fanwe.lib.adapter.http.model.SDRequestParams;
import com.fanwe.lib.adapter.http.model.SDResponse;

public abstract class SDRequestCallback
{
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SDRequestParams mRequestParams;
    private SDRequestHandler mRequestHandler;

    public SDRequestCallback()
    {
    }

    public SDRequestHandler getRequestHandler()
    {
        return this.mRequestHandler;
    }

    public void setRequestHandler(SDRequestHandler requestHandler)
    {
        this.mRequestHandler = requestHandler;
    }

    public SDRequestParams getRequestParams()
    {
        return this.mRequestParams;
    }

    public void setRequestParams(SDRequestParams requestParams)
    {
        this.mRequestParams = requestParams;
    }

    public String getCancelTag()
    {
        return this.mRequestParams != null ? this.mRequestParams.getCancelTag() : null;
    }

    public String getActInfo()
    {
        return this.mRequestParams != null ? this.mRequestParams.getActInfo() : "";
    }

    protected void onStartBefore()
    {
    }

    protected void onStart()
    {
    }

    protected void onStartAfter()
    {
    }

    protected void onSuccessBefore(SDResponse resp)
    {
    }

    protected abstract void onSuccess(SDResponse var1);

    protected void onSuccessAfter(SDResponse resp)
    {
    }

    protected void onError(SDResponse resp)
    {
    }

    protected void onCancel(SDResponse resp)
    {
    }

    protected void onFinish(SDResponse resp)
    {
    }

    private void onStartInternal()
    {
        this.onStartBefore();
        this.onStart();
        this.onStartAfter();
    }

    private void onSuccessInternal(SDResponse resp)
    {
        this.onSuccessBefore(resp);
        this.onSuccess(resp);
        this.onSuccessAfter(resp);
    }

    private void onErrorInternal(SDResponse resp)
    {
        this.onError(resp);
    }

    private void onCancelInternal(SDResponse resp)
    {
        this.onCancel(resp);
        this.setRequestHandler((SDRequestHandler) null);
    }

    private void onFinishInternal(SDResponse resp)
    {
        this.onFinish(resp);
        this.setRequestHandler((SDRequestHandler) null);
    }

    public void notifyStart()
    {
        if (this.isMainLooper())
        {
            this.onStartInternal();
        } else
        {
            this.mHandler.post(new Runnable()
            {
                public void run()
                {
                    SDRequestCallback.this.onStartInternal();
                }
            });
        }

    }

    public void notifySuccess(final SDResponse resp)
    {
        if (this.isMainLooper())
        {
            this.onSuccessInternal(resp);
        } else
        {
            this.mHandler.post(new Runnable()
            {
                public void run()
                {
                    SDRequestCallback.this.onSuccessInternal(resp);
                }
            });
        }

    }

    public void notifyError(final SDResponse resp)
    {
        if (this.isMainLooper())
        {
            this.onErrorInternal(resp);
        } else
        {
            this.mHandler.post(new Runnable()
            {
                public void run()
                {
                    SDRequestCallback.this.onErrorInternal(resp);
                }
            });
        }

    }

    public void notifyCancel(final SDResponse resp)
    {
        if (this.isMainLooper())
        {
            this.onCancelInternal(resp);
        } else
        {
            this.mHandler.post(new Runnable()
            {
                public void run()
                {
                    SDRequestCallback.this.onCancelInternal(resp);
                }
            });
        }

    }

    public void notifyFinish(final SDResponse resp)
    {
        if (this.isMainLooper())
        {
            this.onFinishInternal(resp);
        } else
        {
            this.mHandler.post(new Runnable()
            {
                public void run()
                {
                    SDRequestCallback.this.onFinishInternal(resp);
                }
            });
        }

    }

    private boolean isMainLooper()
    {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
