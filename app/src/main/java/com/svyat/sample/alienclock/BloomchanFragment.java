package com.svyat.sample.alienclock;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListAdapter;

import com.svyat.sample.alienclock.alien.AlienFragment;
import com.svyat.sample.alienclock.alien.AlienIncubator;
import com.svyat.sample.alienclock.alien.DefaultAlienIncubator;
import com.svyat.sample.alienclock.controller.AlienController;
import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataContract;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shromyak on 07.07.2016.
 *
 * This fragment is used to show info about Bloomberg channels as List
 */
public class BloomchanFragment extends ListFragment implements AlienFragment, LoaderManager.LoaderCallbacks<Cursor> {

    private final static int DEFAULT_LOADER = 0;

    private final static AtomicInteger counter = new AtomicInteger(0);

    @SuppressWarnings("unused")
    private AlienController controller;
    private AlienIncubator alienIncubator;

    public BloomchanFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        counter.incrementAndGet();
        super.onCreate(savedInstanceState);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(DEFAULT_LOADER, null, this);
    }

    @Override
    public void onDestroy() {
        if (counter.decrementAndGet() == 0) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.destroyLoader(DEFAULT_LOADER);
        }
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAlien();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader loader = new CursorLoader(getContext()
                , Uri.parse(BloomchanDataContract.BLOOMCHAN_CONTENT_URI_STRING)
                , BloomchanDataContract.PROJECTION, null, null, null);

        loader.setUpdateThrottle(3000L);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        ListAdapter adapter = getListAdapter();

        if (adapter == null) {

            adapter = new SimpleCursorAdapter(getContext(), android.R.layout.two_line_list_item, data
                    , new String[] {BloomchanDataContract.Bloomchan.TITLE, BloomchanDataContract.Bloomchan.PUB_DATE}
                    , new int [] {android.R.id.text1, android.R.id.text2}
                    , 0);

            setListAdapter(adapter);

        } else if (adapter instanceof SimpleCursorAdapter){

            SimpleCursorAdapter simpleCursorAdapter = (SimpleCursorAdapter) adapter;
            simpleCursorAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.cancelLoad();
        loader.forceLoad();
    }

    private AlienIncubator getAlienIncubator() {

        if (alienIncubator == null) {
            alienIncubator = DefaultAlienIncubator.get(getContext());
        }

        return alienIncubator;
    }

    private void initAlien() {
        getAlienIncubator().visit(this, null);
    }

    @Override
    public void plantAlien(AlienController controller) {
        this.controller = controller;
    }
}
