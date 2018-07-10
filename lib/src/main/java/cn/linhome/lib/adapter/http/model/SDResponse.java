package cn.linhome.lib.adapter.http.model;

public class SDResponse
{
    private String result;
    private String decryptedResult;
    private int httpCode;
    private Throwable throwable;

    public SDResponse()
    {
    }

    public SDResponse setThrowable(Throwable throwable)
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

    public SDResponse setResult(String result)
    {
        this.result = result;
        return this;
    }

    public int getHttpCode()
    {
        return this.httpCode;
    }

    public SDResponse setHttpCode(int httpCode)
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