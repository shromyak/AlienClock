package com.svyat.sample.alienclock.net.source.bloomchan;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.svyat.sample.alienclock.data.AlienModelMapper;
import com.svyat.sample.alienclock.data.DataIntegrityException;
import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract;
import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataIntegrator;
import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataModel;
import com.svyat.sample.alienclock.net.AlienDownloader;
import com.svyat.sample.alienclock.net.retrofit.RetrofitAbstractDownloader;
import com.svyat.sample.alienclock.net.retrofit.RetrofitException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;

/**
 * Created by MAC on 09.07.16.
 */
public class BloomchanDownloader extends RetrofitAbstractDownloader<BloomchanDataModel.RootElement> implements AlienDownloader {

    private final static String LOG_TAG = BloomchanDownloader.class.getSimpleName();

    private final AtomicBoolean isActive = new AtomicBoolean(false);

    private final Context context;

    public BloomchanDownloader(Context context) throws RetrofitException {
        super(BloomchanDataIntegrator.DownloadRequestFactory.class, new BloomchanDataIntegrator());

        this.context = context;
    }

    @Override
    public void start() {

        isActive.set(true);

        startDownloadRequest();

        isActive.set(false);
    }

    @Override
    public void forceStop() {
        super.stop();
        isActive.set(false);
    }

    @Override
    public boolean isActive() {
        return isActive.get();
    }

    @Override
    protected synchronized void processResponse(BloomchanDataModel.RootElement root) {

        if (root != null && root.channel != null && root.channel.items != null) {

            AlienModelMapper mapper = getDataIntegrator().getMapper();
            ArrayList<ContentValues> values = new ArrayList<>();

            for (BloomchanDataModel.Item item:root.channel.items) {

                try {

                    item.normalize();
                    values.add(mapper.toContentValues(item));

                } catch (DataIntegrityException e) {

                    Log.e(getLogTag(), "Can't normalize item", e);
                }
            }

            int res = context.getContentResolver()
                    .bulkInsert(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING),
                            values.toArray(new ContentValues[0]));

            count.set(res);
        }
    }

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }

    @Override
    protected Call<BloomchanDataModel.RootElement> createRequest(Object pretender) {

        if (pretender instanceof BloomchanDataIntegrator.DownloadRequestFactory) {

            BloomchanDataIntegrator.DownloadRequestFactory factory =
                    (BloomchanDataIntegrator.DownloadRequestFactory) pretender;

            return factory.requestData();
        }

        return null;
    }

    /**
     * **********************************************************************
     *          This part is for ANDROID TEST CASE
     *
     * Please refer:
     * @link GcmbasedDownloadTaskServiceTest#testGcmBasedDownload_3_GetTest
     * **********************************************************************
     */
    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public boolean test() {
        start();
        return count.get() > 0;
    }
}
