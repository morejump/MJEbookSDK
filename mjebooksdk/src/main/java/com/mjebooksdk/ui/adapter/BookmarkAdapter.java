package com.mjebooksdk.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mjebooksdk.R;
import com.mjebooksdk.database.ReadLocation;
import com.mjebooksdk.ui.callbacks.IBookmarkListener;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    List<ReadLocation> data;
    IBookmarkListener bookmarkListener;

    public BookmarkAdapter(List<ReadLocation> data, IBookmarkListener bookmarkListener) {
        this.data = data;
        this.bookmarkListener = bookmarkListener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view, bookmarkListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ReadLocation> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private IBookmarkListener bookmarkListener;
        private ImageView imgDelete, imgEdit;
        private TextView txtTitle;
        private ReadLocation readLocation;

        public BookmarkViewHolder(@NonNull View itemView, IBookmarkListener bookmarkListener) {
            super(itemView);
            this.bookmarkListener = bookmarkListener;
            itemView.setOnClickListener(this);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgDelete.setOnClickListener(this);
            imgEdit.setOnClickListener(this);
        }

        public void bindData(ReadLocation readLocation) {
            txtTitle.setText(readLocation.getTitle());
            this.readLocation = readLocation;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.imgDelete) {
                bookmarkListener.deleteBookmark(readLocation);
                return;
            } else if (id == R.id.imgEdit) {
                bookmarkListener.updateBookmark(readLocation);
                return;
            }
            bookmarkListener.selectedItem(readLocation);
        }
    }
}
