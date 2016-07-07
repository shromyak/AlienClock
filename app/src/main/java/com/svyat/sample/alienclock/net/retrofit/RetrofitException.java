package com.svyat.sample.alienclock.net.retrofit;

/**
 * Created by shromyak on 12.07.2016.
 */
public class RetrofitException extends Exception {

    public RetrofitException() {
        super();
    }

    public RetrofitException(String message) {
        super(message);
    }

    public RetrofitException(Exception ex) {
        super(ex);
    }

    public RetrofitException(String message, Exception ex) {
        super(message, ex);
    }
}
