package com.svyat.sample.alienclock.net.retrofit;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by shromyak on 11.07.2016.
 */
public abstract class RetrofitAbstractDownloader<T> {

    private final RetrofitDataIntegrator dataIntegrator;

    private final Class<?> requestFactoryClazz;

    private final Object requestFactory;

    private final Object LOCK;

    private Call<T> call;

    protected RetrofitAbstractDownloader(Class<?> requestFactoryClazz, RetrofitDataIntegrator dataIntegrator) throws RetrofitException {

        this.LOCK = new Object();

        this.requestFactoryClazz = requestFactoryClazz;

        this.dataIntegrator = dataIntegrator;

        try {

            this.requestFactory = AbstractFactory.createRequestFactory(requestFactoryClazz, dataIntegrator);

        } catch (Exception e) {

            throw new RetrofitException("Can't instantiate request prototype", e);
        }
    }

    protected RetrofitDataIntegrator getDataIntegrator() {
        return dataIntegrator;
    }

    protected abstract void processResponse(T data);

    protected abstract String getLogTag();

    protected abstract Call<T> createRequest(Object requestFactory);

    protected void stop() {

        if (call != null && call.isExecuted()) {

            try {

                synchronized (LOCK) {
                    Method method = requestFactoryClazz.getMethod("cancel");
                    method.invoke(call);
                    call = null;
                }

            } catch (NoSuchMethodException ignorable) {
            } catch (IllegalAccessException ignorable) {
            } catch (InvocationTargetException ignorable) {
            }
        }
    }

    protected synchronized void startDownloadRequest() {

        call = createRequest(requestFactory);

        try {

            //CANCELLABLE REQUEST
            Response<T> result = call.execute();

            boolean needProcess;
            synchronized (LOCK) {
                needProcess = call != null && !call.isCanceled();
                call = null;
            }

            if (result.isSuccessful() && needProcess) {

                processResponse(result.body());
            }

        } catch (IOException e) {

            synchronized (LOCK) {
                call = null;
            }

            Log.e(getLogTag(), "Something went frong with request", e);
        }
    }

    private static class AbstractFactory {

        public static <O> O createRequestFactory(Class<O> clazz,  RetrofitDataIntegrator dataIntegrator) throws Exception {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(dataIntegrator.getBaseUrl())
                    .client(new OkHttpClient.Builder().build());

            for (Converter.Factory factory: dataIntegrator.getConverterFactories()) {

                builder.addConverterFactory(factory);
            }

            return builder.build().create(clazz);
        }
    }
}
