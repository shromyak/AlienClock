package com.svyat.sample.alienclock.net.retrofit;

/**
 * Created by shromyak on 12.07.2016.
 *
 * Common exception class for operations performed with Retrofit
 */
public class RetrofitException extends Exception {

    public RetrofitException(String message, Exception ex) {
        super(message, ex);
    }
}
