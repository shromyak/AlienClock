package com.svyat.sample.alienclock.data;

/**
 * Created by shromyak on 11.07.2016.
 */
public class DataIntegrityException extends Exception {

    public DataIntegrityException() {
        super();
    }

    public DataIntegrityException(String message) {
        super(message);
    }

    public DataIntegrityException(Exception ex) {
        super(ex);
    }

    public DataIntegrityException(String message, Exception ex) {
        super(message, ex);
    }
}
