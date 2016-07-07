package com.svyat.sample.alienclock.data.bloomchan;

import android.text.TextUtils;

import com.svyat.sample.alienclock.data.AlienData;
import com.svyat.sample.alienclock.data.DataIntegrityException;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by shromyak on 07.07.2016.
 */

public class BloomchanDataModel {

    private static final SimpleDateFormat bloomDateFormatter = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

    @Root(name = "rss", strict = false)
    public static class RootElement {

        @Element(name = "channel", required = false)
        public Channel channel;

    }

    @Root(name = "channel", strict = false)
    public static class Channel {

        @ElementList(inline = true, name = "item", required = false)
        public List<Item> items;

    }

    @Root(name = "item", strict = false)
    public static class Item implements AlienData {

        @Attribute(required = false)
        public long _id = -1;

        @Element(name = "title", data = true)
        public String title;

        @Element(name = "pubDate")
        public String pubDate;

        @Element(name = "link")
        public String link;

        @Element(name = "enclosure", required = false)
        public Enclosure preenclosure;

        @Attribute(required = false)
        public String enclosure;

        @Element(name = "guid")
        public String guid;

        @Attribute(required = false)
        public long created;

        @Override
        public void normalize() throws DataIntegrityException {

            if (TextUtils.isEmpty(title)) {

                throw new DataIntegrityException("Title is empty");
            }

            if (TextUtils.isEmpty(link)) {

                throw new DataIntegrityException("Link is empty");
            }

            if (TextUtils.isEmpty(pubDate)) {

                throw new DataIntegrityException("PubDate is empty");
            }

            if (TextUtils.isEmpty(guid)) {

                throw new DataIntegrityException("GUID is empty");
            }

            try {

                Date ddt = bloomDateFormatter.parse(pubDate);
                created = ddt.getTime();

            } catch (ParseException e) {

                throw new DataIntegrityException("Wrong date format", e);
            }

            if (created <=0) {

                throw new DataIntegrityException("Wrong date format");
            }

            if (preenclosure != null) {
                enclosure = preenclosure.toString();
            }
        }
    }

    @Root(name = "enclosure", strict = false)
    public static class Enclosure {

        @Attribute(name = "url")
        public String url;

        @Attribute(name = "length")
        public String length;

        @Attribute(name = "type")
        public String type;

        @Override
        public String toString() {
            return "<enclosure url=\""+url+"\" length=\""+length+"\" type=\""+type+"\"/>";
        }
    }
}
