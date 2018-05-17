package com.fanwe.lib.adapter.http.callback;

import android.text.TextUtils;

import com.fanwe.lib.adapter.http.model.SDResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class SDModelRequestCallback<T> extends SDRequestCallback
{
    public T actModel;
    private Class<T> mModelClass;

    public SDModelRequestCallback()
    {
    }

    public T getActModel()
    {
        return this.actModel;
    }

    public Class<T> getModelClass()
    {
        return this.mModelClass;
    }

    protected void onStartBefore()
    {
        Type type = this.getType(this.getClass(), 0);
        if (type instanceof Class)
        {
            this.mModelClass = (Class) type;
        }

    }

    protected void onSuccessBefore(SDResponse resp)
    {
        if (this.mModelClass != null)
        {
            String result = resp.getDecryptedResult();
            if (TextUtils.isEmpty(result))
            {
                result = resp.getResult();
            }

            this.actModel = this.parseActModel(result, this.mModelClass);
        }

    }

    protected abstract <T> T parseActModel(String var1, Class<T> var2);

    public Type getType(Class<?> clazz, int index)
    {
        Type type = null;
        Type[] types = this.getType(clazz);
        if (types != null && index >= 0 && types.length > index)
        {
            type = types[index];
        }

        return type;
    }

    private Type[] getType(Class<?> clazz)
    {
        Type[] types = null;
        if (clazz != null)
        {
            Type type = clazz.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            types = parameterizedType.getActualTypeArguments();
        }

        return types;
    }
}
