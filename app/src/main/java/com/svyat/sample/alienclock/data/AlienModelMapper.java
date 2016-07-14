package com.svyat.sample.alienclock.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by shromyak on 09.07.16.
 *
 * One leg interface for
 * Mapper <AlienData, ContentValues>
 */
public interface AlienModelMapper<T extends AlienData> {

    ContentValues toContentValues(T t);

    T fromCurrentRow(Cursor cursor);

}