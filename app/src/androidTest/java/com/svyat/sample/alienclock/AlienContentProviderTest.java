package com.svyat.sample.alienclock;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.TextUtils;

import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract;
import com.svyat.sample.alienclock.data.AlienContentProvider;
import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataIntegrator;
import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataModel;

import org.junit.runner.RunWith;

/**
 * Created by shromyak on 09.07.16.
 *
 * Tests for Content Provider
 */
@RunWith(AndroidJUnit4.class)
public class AlienContentProviderTest extends ProviderTestCase2<AlienContentProvider> {

    public AlienContentProviderTest() {

        super(AlienContentProvider.class, BloomchanDataContract.AUTHORITY);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testAlienContentProvider_1_GetType() {
        String type = getMockContentResolver().getType(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING));
        assert TextUtils.equals(type, BloomchanDataContract.MIME_TYPE_DIR);

        type = getMockContentResolver().getType(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING +"/0"));
        assert TextUtils.equals(type, BloomchanDataContract.MIME_TYPE_ITEM);

        type = getMockContentResolver().getType(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING +"?exp=12"));
        assert TextUtils.equals(type, BloomchanDataContract.MIME_TYPE_DIR);

        type = getMockContentResolver().getType(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING +"/0?exp=12"));
        assert TextUtils.equals(type, BloomchanDataContract.MIME_TYPE_ITEM);

        type = getMockContentResolver().getType(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING.replace("messagedata", "nomessagedata")));
        assert !TextUtils.equals(type, BloomchanDataContract.MIME_TYPE_DIR);
    }

    @SmallTest
    public void testAlienContentProvider_2_GetRows() {
        Cursor cursor = getMockContentResolver().query(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING), BloomchanDataContract.PROJECTION, null, null, null);
        assert cursor != null;
        cursor.close();
    }

    @SmallTest
    public void testAlienContentProvider_3_GetRow() {
        Cursor cursor = getMockContentResolver().query(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING +"/0"), BloomchanDataContract.PROJECTION, null, null, null);
        assert cursor != null;
        cursor.close();
    }

    @SmallTest
    public void testAlienContentProvider_4_CRUD() {

        BloomchanDataIntegrator integrator = new BloomchanDataIntegrator();

        //CREATE
        BloomchanDataModel.Item data = new BloomchanDataModel.Item();
        data.title = "title";
        data.pubDate = "pubDate";
        data.link = "link";
        data.guid = "guid";
        ContentValues values = integrator.getMapper().toContentValues(data);

        Uri res = getMockContentResolver().insert(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING), values);
        assert res != null;
        long recno = Long.parseLong(res.getLastPathSegment());

        //READ
        Cursor cursor = getMockContentResolver().query(res, BloomchanDataContract.PROJECTION, null, null, null);
        assert cursor != null;
        assert cursor.getCount() == 1;

        cursor.moveToNext();

        String title = cursor.getString(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.TITLE));
        assert "title".equals(title);

        String pubDate = cursor.getString(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.PUB_DATE));
        assert "pubDate".equals(pubDate);

        String link = cursor.getString(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.LINK));
        assert "link".equals(link);

        String guid = cursor.getString(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.GUID));
        assert "guid".equals(guid);

        long idn = cursor.getLong(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.ID));
        assert idn == recno;

        cursor.close();

        //UPDATE
        values.put(BloomchanDataContract.Bloomchan.LINK, "link2");

        int cnt = getMockContentResolver().update(res, values, null, null);
        assert cnt == 1;

        //read again to check
        cursor = getMockContentResolver().query(res, BloomchanDataContract.PROJECTION, null, null, null);
        assert cursor != null;
        assert cursor.getCount() == 1;

        cursor.moveToNext();

        link = cursor.getString(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.LINK));
        assert "link2".equals(link);

        idn = cursor.getLong(cursor.getColumnIndex(BloomchanDataContract.Bloomchan.ID));
        assert idn == recno;

        cursor.close();

        //DELETE
        cnt = getMockContentResolver().delete(res, null, null);
        assert cnt == 1;
    }

    @SmallTest
    public void testAlienContentProvider_5_CRUD_with_conflict() {

        BloomchanDataIntegrator integrator = new BloomchanDataIntegrator();

        BloomchanDataModel.Item data = new BloomchanDataModel.Item();
        data.title = "title";
        data.pubDate = "pubDate";
        data.link = "link";
        data.guid = "guid";
        ContentValues values = integrator.getMapper().toContentValues(data);

        //UPDATE WITH CONFLICT (record doesn't exist)
        int cnt = getMockContentResolver().update(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING), values, null, null);
        assert cnt == 0;

        //CREATE
        Uri res = getMockContentResolver().insert(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING), values);
        assert res != null;

        long recno = Long.parseLong(res.getLastPathSegment());
        assert recno == 1;

        //INSERT WITH CONFLICT (guid1 == guid2 and must be unique)
        res = getMockContentResolver().insert(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING), values);
        assert res != null;

        //DELETE
        cnt = getMockContentResolver().delete(Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING)
                , BloomchanDataContract.Bloomchan.GUID + " = ?", new String[] {"guid"});
        assert cnt == 1;
    }
}
