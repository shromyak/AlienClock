package com.svyat.sample.alienclock.data.bloomchan;

/**
 * Created by shromyak on 07.07.2016.
 */
public class BloomchanDataContract {

    public static final String AUTHORITY = "com.svyat.sample.alienclock.provider";

    public static final String BLOOMCHAN_CONTENT_URI_STRING = "content://com.svyat.sample.alienclock.provider/bloomchan";

    public static final String TABLE_BLOOMCHAN = "bloomchan";

    public static final class Bloomchan {

        public static final String ID = "_ID";

        public static final String TITLE = "title";

        public static final String PUB_DATE = "pubDate";

        public static final String LINK = "link";

        public static final String CREATED = "created";

        public static final String ENCLOSURE = "enclosure";

        public static final String GUID = "guid";
    }

    public static final String [] PROJECTION = new String[] {Bloomchan.ID, Bloomchan.TITLE, Bloomchan.PUB_DATE, Bloomchan.LINK, Bloomchan.CREATED, Bloomchan.ENCLOSURE, Bloomchan.GUID};

    public static final String REFRESH_CLAUSE = Bloomchan.CREATED + "> ?";

    public static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/vnd.svyat.sample.alienclock.bloomchan";

    public static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/vnd.svyat.sample.alienclock.bloomchan";
}
