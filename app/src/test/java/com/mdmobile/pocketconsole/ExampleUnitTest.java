package com.mdmobile.pocketconsole;

import com.mdmobile.pocketconsole.UI.LoginConfigureServerFragment;
import com.mdmobile.pocketconsole.utils.GeneralUtility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testUrlValidation(){

        String finalUrl = "https://uk.mobicontrolcloud.com";

        String inputUrl = "uk.mobicontrolcloud.com/";
        String inputUrl1 = "https://uk.mobicontrolcloud.com/";
        String inputUrl2 = "uk.mobicontrolcloud.com/";

        String returnedURL = GeneralUtility.validateUrl(inputUrl);
        assertTrue("Url returned does not match: "+returnedURL,returnedURL.equals(finalUrl));

        returnedURL = GeneralUtility.validateUrl(inputUrl1);
        assertTrue("Url returned does not match: "+returnedURL,returnedURL.equals(finalUrl));

        returnedURL = GeneralUtility.validateUrl(inputUrl2);
        assertTrue("Url returned does not match: "+returnedURL,returnedURL.equals(finalUrl));

    }
}