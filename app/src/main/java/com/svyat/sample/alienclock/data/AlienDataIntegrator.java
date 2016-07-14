package com.svyat.sample.alienclock.data;

/**
 * Created by shromyak on 09.07.16.
 *
 * Data Integrator is gatekeeper between external and internal data sources
 * It works in pair with DataMapper
 *
 * @link AlienDataMapper
 */
public interface AlienDataIntegrator {

    AlienModelMapper<? extends AlienData> getMapper();
    String getBaseUrl();
}
