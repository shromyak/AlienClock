package com.svyat.sample.alienclock.net.retrofit;

import com.svyat.sample.alienclock.data.AlienDataIntegrator;

import retrofit2.Converter;

/**
 * Created by shromyak on 12.07.2016.
 */
public interface RetrofitDataIntegrator extends AlienDataIntegrator {

    Converter.Factory [] getConverterFactories();
}
