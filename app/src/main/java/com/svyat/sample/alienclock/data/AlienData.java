package com.svyat.sample.alienclock.data;

/**
 * Created by shromyak on 11.07.2016.
 */
public interface AlienData {

    //Integrity validation
    void normalize() throws DataIntegrityException;

}
