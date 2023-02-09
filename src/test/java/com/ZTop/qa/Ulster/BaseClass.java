package com.ZTop.qa.Ulster;

import com.ZTop.qa.TestNGHelper;

public class BaseClass extends TestNGHelper {

    String login = "test_username";
    String password = "test_password";

    static {
        testName = "Screenshot Test";
        urlDomain = System.getenv("SITE_URL");
        hasNetwork = false;
        hasVisual = false;
        hasVideo = true;
        hasConsole = false;
    }
}
