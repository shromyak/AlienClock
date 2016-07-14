package com.svyat.sample.alienclock;

import android.text.TextUtils;

import com.svyat.sample.alienclock.net.AlienDownloader;
import com.svyat.sample.alienclock.utils.StringUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by shromyak on 09.07.16.
 *
 * Test main string functionality
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class StringUtilsTest {

    @Before
    public void setup(){
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    public void csvParse_isCorrect() throws Exception {

        List<String> lst = StringUtils.splitCsv(" 1 ,\t 2, 3\t, 4\t\n");

        assertArrayEquals(lst.toArray(new String[lst.size()]), new String [] {"1", "2", "3", "4"});
    }

    @Test
    public void enumJoinToCsv_isCorrect() throws Exception {

        String csv = StringUtils.enumNamesToCsv(AlienDownloader.DownloaderType.class);

        assert csv.length() > 0;
    }
}
