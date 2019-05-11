package com.mjebooksdk.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mjebooksdk.R;
import com.mjebooksdk.database.dao.IReadLocationDao;
import com.mjebooksdk.database.dao.MockReadLocationImpl;
import com.mjebooksdk.ui.adapter.BookmarkAdapter;
import com.mjebooksdk.util.DatabaseManager;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView rvBookmark;
    private BookmarkAdapter bookmarkAdapter;
    private IReadLocationDao readLocationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        readLocationDao = DatabaseManager.getInstance();
        bookmarkAdapter = new BookmarkAdapter(readLocationDao.getAll());
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
}
