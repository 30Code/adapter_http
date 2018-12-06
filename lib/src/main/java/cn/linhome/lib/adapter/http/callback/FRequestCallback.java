package cn.linhome.lib.adapter.http.callback;

import android.os.Handler;
import android.os.Looper;

import cn.linhome.lib.adapter.http.handler.FRequestHandler;
import cn.linhome.lib.adapter.http.model.FRequestParams;
import cn.linhome.lib.adapter.http.model.FResponse;

public abstract class FRequestCallback
{
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private FRequestParams mRequestParams;
    private FRequestHandler mRequestHandler;

    public FRequestCallback()
    {
    }

    public FRequestHandler getRequestHandler()
    {
        return this.mRequestHandler;
    }

    public void setRequestHandler(FRequestHandler requestHandler)
    {
        this.mRequestHandler = requestHandler;
    }

    public FRequestParams getRequestParams()
    {
        return this.mRequestParams;
    }

    public void setRequestParams(FRequestParams requestParams)
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

    protected void onSuccessBefore(FResponse resp)
    {
    }

    protected abstract void onSuccess(FResponse var1);

    protected void onSuccessAfter(FResponse resp)
    {
    }

    protected void onError(FResponse resp)
    {
    }

    protected void onCancel(FResponse resp)
    {
    }

    protected void onFinish(FResponse resp)
    {
    }

    private void onStartInternal()
    {
        this.onStartBefore();
        this.onStart();
        this.onStartAfter();
    }

    private void onSuccessInternal(FResponse resp)
    {
        this.onSuccessBefore(resp);
        this.onSuccess(resp);
        this.onSuccessAfter(resp);
    }

    private void onErrorInternal(FResponse resp)
    {
        this.onError(resp);
    }

    private void onCancelInternal(FResponse resp)
    {
        this.onCancel(resp);
        this.setRequestHandler((FRequestHandler) null);
    }

    private void onFinishInternal(FResponse resp)
    {
        this.onFinish(resp);
        this.setRequestHandler((FRequestHandler) null);
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
                    FRequestCallback.this.onStartInternal();
                }
            });
        }

    }

    public void notifySuccess(final FResponse resp)
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
                    FRequestCallback.this.onSuccessInternal(resp);
                }
            });
        }

    }

    public void notifyError(final FResponse resp)
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
                    FRequestCallback.this.onErrorInternal(resp);
                }
            });
        }

    }

    public void notifyCancel(final FResponse resp)
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
                    FRequestCallback.this.onCancelInternal(resp);
                }
            });
        }

    }

    public void notifyFinish(final FResponse resp)
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
                    FRequestCallback.this.onFinishInternal(resp);
                }
            });
        }

    }

    private boolean isMainLooper()
    {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
