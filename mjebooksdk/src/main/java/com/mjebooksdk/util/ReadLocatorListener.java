package com.mjebooksdk.util;

import com.mjebooksdk.database.locators.ReadLocator;

/**
 * Created by Hrishikesh Kadam on 17/04/2018.
 */
public interface ReadLocatorListener {

    void saveReadLastLocator(ReadLocator readLocator);
    void saveReadCurrentLocator(ReadLocator readLocator);
}