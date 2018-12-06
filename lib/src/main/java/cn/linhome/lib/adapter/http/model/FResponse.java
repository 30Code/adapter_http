package cn.linhome.lib.adapter.http.model;

public class FResponse
{
    private String result;
    private String decryptedResult;
    private int httpCode;
    private Throwable throwable;

    public FResponse()
    {
    }

    public FResponse setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
        return this;
    }

    public Throwable getThrowable()
    {
        return this.throwable;
    }

    public String getResult()
    {
        return this.result;
    }

    public FResponse setResult(String result)
    {
        this.result = result;
        return this;
    }

    public int getHttpCode()
    {
        return this.httpCode;
    }

    public FResponse setHttpCode(int httpCode)
    {
        this.httpCode = httpCode;
        return this;
    }

    public String getDecryptedResult()
    {
        return this.decryptedResult;
    }

    public void setDecryptedResult(String decryptedResult)
    {
        this.decryptedResult = decryptedResult;
    }
}