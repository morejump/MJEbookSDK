package com.mjebooksdk.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mjebooksdk.R;
import com.mjebooksdk.database.ReadLocation;
import com.mjebooksdk.database.dao.IReadLocationDao;
import com.mjebooksdk.ui.adapter.BookmarkAdapter;
import com.mjebooksdk.ui.callbacks.IBookmarkListener;
import com.mjebooksdk.ui.callbacks.IDeleteBookmarkDialogListener;
import com.mjebooksdk.ui.callbacks.IUpdateBookmarkDialogListner;
import com.mjebooksdk.ui.dialogs.AddBookmarkDialog;
import com.mjebooksdk.ui.dialogs.DeleteBookmarkDialog;
import com.mjebooksdk.ui.dialogs.UpdateBookmarkDialog;
import com.mjebooksdk.util.DatabaseManager;

public class BookmarkActivity extends AppCompatActivity implements IBookmarkListener {

    private RecyclerView rvBookmark;
    private BookmarkAdapter bookmarkAdapter;
    private IReadLocationDao readLocationDao;
    private DeleteBookmarkDialog deleteBookmarkDialog;
    private UpdateBookmarkDialog updateBookmarkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        updateBookmarkDialog = new UpdateBookmarkDialog(this);
        deleteBookmarkDialog = new DeleteBookmarkDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        readLocationDao = DatabaseManager.getInstance();
        bookmarkAdapter = new BookmarkAdapter(readLocationDao.getAll(), this);
        rvBookmark = findViewById(R.id.rvBookmark);
        rvBookmark.setLayoutManager(new LinearLayoutManager(this));
        rvBookmark.setAdapter(bookmarkAdapter);
        bookmarkAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void deleteBookmark(final ReadLocation readLocation) {
        deleteBookmarkDialog.show();
        deleteBookmarkDialog.setDeleteListener(new IDeleteBookmarkDialogListener() {
            @Override
            public void deleteBookmark() {
                readLocationDao.delete(readLocation.getId());

            }
        });
    }

    @Override
    public void updateBookmark(final ReadLocation readLocation) {
        updateBookmarkDialog.setUpdateListner(new IUpdateBookmarkDialogListner() {
            @Override
            public void updateBookmark() {
                readLocationDao.update(readLocation);
            }
        });
    }
}
