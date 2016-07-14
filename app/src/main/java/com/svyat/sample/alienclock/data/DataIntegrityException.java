package com.svyat.sample.alienclock.data;

/**
 * Created by shromyak on 11.07.2016.
 *
 * Common Exception for data integration (conversion) operations
 */
public class DataIntegrityException extends Exception {

    public DataIntegrityException(String message) {
        super(message);
    }

    public DataIntegrityException(String message, Exception ex) {
        super(message, ex);
    }
}
