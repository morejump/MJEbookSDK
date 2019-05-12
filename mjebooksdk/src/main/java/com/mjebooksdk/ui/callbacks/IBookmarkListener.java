package com.mjebooksdk.ui.callbacks;

import com.mjebooksdk.database.ReadLocation;

public interface IBookmarkListener {
    void deleteBookmark(ReadLocation readLocation);
    void updateBookmark(ReadLocation readLocation);
    void selectedItem(ReadLocation readLocation);
}
