package com.svyat.sample.alienclock.data.bloomchan;

import com.svyat.sample.alienclock.data.AlienModelMapper;
import com.svyat.sample.alienclock.net.retrofit.RetrofitDataIntegrator;

import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by shromyak on 11.07.2016.
 */
public class BloomchanDataIntegrator implements RetrofitDataIntegrator {

    private static final BloomchanDataMapper mapper = new BloomchanDataMapper();

    @Override
    public AlienModelMapper<BloomchanDataModel.Item> getMapper() {
        return mapper;
    }

    @Override
    public String getBaseUrl() {
        return "https://www.bloomberg.com";
    }

    @Override
    public Converter.Factory[] getConverterFactories() {

        //Order is important, Retrofit uses first converter as default till failure
        return new Converter.Factory[] {
                SimpleXmlConverterFactory.create()
                , GsonConverterFactory.create()
        };
    }

    //Retrofit doesn't allow inheritance of this interface from another interface (so all Generic types work through Reflexion)
    public interface DownloadRequestFactory {
        @Headers({
                "Accept: application/xml",//,application/xhtml+xml,text/html
                "User-Agent: Retrofit"
        })
        @GET("/feeds/podcasts/etf_report.xml")
        Call<BloomchanDataModel.RootElement> requestData();
    }
}
