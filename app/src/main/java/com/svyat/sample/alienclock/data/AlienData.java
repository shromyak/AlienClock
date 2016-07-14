package com.svyat.sample.alienclock.data;

/**
 * Created by shromyak on 11.07.2016.
 *
 * Simple interface that allow to trigger verification, validation and pre-processing
 * data achieved from external source (Internet)
 * before storing it into persistence
 */
public interface AlienData {

    //Integrity validation
    void normalize() throws DataIntegrityException;

}
