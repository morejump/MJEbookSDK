package com.mjebooksdk.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
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
import com.mjebooksdk.ui.dialogs.DeleteBookmarkDialog;
import com.mjebooksdk.ui.dialogs.UpdateBookmarkDialog;
import com.mjebooksdk.util.DatabaseManager;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements IBookmarkListener {

    private RecyclerView rvBookmark;
    private BookmarkAdapter bookmarkAdapter;
    private IReadLocationDao readLocationDao;
    private DeleteBookmarkDialog deleteBookmarkDialog;
    private UpdateBookmarkDialog updateBookmarkDialog;
    public static String BOOKMARK_RESULT_KEY = "BOOKMARK_RESULT_KEY";
    public List<ReadLocation> readLocations;
    public TextView txtNoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        txtNoItem = findViewById(R.id.txtNoItem);
        updateBookmarkDialog = new UpdateBookmarkDialog(this);
        deleteBookmarkDialog = new DeleteBookmarkDialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        readLocationDao = DatabaseManager.getInstance();
        readLocations = readLocationDao.getAllReadLocation();
        doShowHideNoti();
        bookmarkAdapter = new BookmarkAdapter(readLocations, this);
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
                readLocationDao.deleteReadLoctionById(readLocation.getId());
                readLocations = readLocationDao.getAllReadLocation();
                doShowHideNoti();
                bookmarkAdapter.setData(readLocations);
            }
        });
    }

    @Override
    public void updateBookmark(final ReadLocation readLocation) {
        updateBookmarkDialog.show();
        updateBookmarkDialog.setUpdateListner(new IUpdateBookmarkDialogListner() {
            @Override
            public void updateBookmark(String title) {
                readLocationDao.updateReadLocationByTitle(readLocation, title);
                readLocations = readLocationDao.getAllReadLocation();
                doShowHideNoti();
                bookmarkAdapter.setData(readLocations);
            }
        });

    }

    @Override
    public void selectedItem(ReadLocation readLocation) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(BOOKMARK_RESULT_KEY, readLocation.getId());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void doShowHideNoti(){
        if (readLocations.size() == 0) {
            txtNoItem.setVisibility(View.VISIBLE);
        }
        else {
            txtNoItem.setVisibility(View.GONE);
        }
    }
}
