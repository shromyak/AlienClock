package com.svyat.sample.alienclock;

import android.text.TextUtils;

import com.svyat.sample.alienclock.data.bloomchan.BloomchanDataModel;
import com.svyat.sample.alienclock.data.DataIntegrityException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by shromyak on 11.07.2016.
 *
 * Test date/time conversion for bloomchan
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class BloomchanDateTest {

    @Before
    public void setup(){
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    public void testBloomchanDate_1_isCorrect() throws Exception {

        BloomchanDataModel.Item data = new BloomchanDataModel.Item();
        data.pubDate = "Fri, 08 Jul 2016 22:07:55 GMT";

        data.normalize();

        assert data.created == 1468015675000L;
    }

    @Test
    public void testBloomchanDate_2_isWrongDateChecked() throws Exception {

        BloomchanDataModel.Item data = new BloomchanDataModel.Item();
        data.pubDate = "08-07-2016T22:07:55+000";

        boolean checked = false;

        try {

            data.normalize();

        } catch (DataIntegrityException ex) {

            checked = true;
        }

        assert checked;
    }
}
