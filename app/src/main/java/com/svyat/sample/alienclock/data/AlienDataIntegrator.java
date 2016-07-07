package com.svyat.sample.alienclock.data;

/**
 * Created by MAC on 09.07.16.
 */
public interface AlienDataIntegrator {

    AlienModelMapper<? extends AlienData> getMapper();
    String getBaseUrl();
}
