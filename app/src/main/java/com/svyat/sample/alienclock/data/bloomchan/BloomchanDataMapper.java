package com.svyat.sample.alienclock.data.bloomchan;

import android.content.ContentValues;
import android.database.Cursor;

import com.svyat.sample.alienclock.data.AlienModelMapper;

import static com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract.Bloomchan.*;
/**
 * Created by MAC on 09.07.16.
 */
public class BloomchanDataMapper implements AlienModelMapper<BloomchanDataModel.Item> {

    @Override
    public ContentValues toContentValues(BloomchanDataModel.Item model) {

        ContentValues contentValues = new ContentValues();

        if (model._id >= 0) {
            contentValues.put(ID, model._id);
        }

        contentValues.put(TITLE, model.title);
        contentValues.put(PUB_DATE, model.pubDate);
        contentValues.put(LINK, model.link);
        contentValues.put(ENCLOSURE, model.enclosure);
        contentValues.put(CREATED, model.created);
        contentValues.put(GUID, model.guid);

        return contentValues;
    }

    @Override
    public BloomchanDataModel.Item fromCurrentRow(Cursor cursor) {

        BloomchanDataModel.Item model = new BloomchanDataModel.Item();

        model._id = cursor.getLong(cursor.getColumnIndex(ID));
        model.title = cursor.getString(cursor.getColumnIndex(TITLE));
        model.pubDate = cursor.getString(cursor.getColumnIndex(PUB_DATE));
        model.link = cursor.getString(cursor.getColumnIndex(LINK));
        model.enclosure = cursor.getString(cursor.getColumnIndex(ENCLOSURE));
        model.created = cursor.getLong(cursor.getColumnIndex(CREATED));
        model.guid = cursor.getString(cursor.getColumnIndex(GUID));

        return model;
    }

}
