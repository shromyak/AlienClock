package com.svyat.sample.alienclock.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by MAC on 09.07.16.
 */
public interface AlienModelMapper<T extends AlienData> {

    ContentValues toContentValues(T t);

    T fromCurrentRow(Cursor cursor);

}