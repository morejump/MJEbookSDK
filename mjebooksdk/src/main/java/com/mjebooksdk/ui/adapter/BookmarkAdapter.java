package com.mjebooksdk.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mjebooksdk.R;
import com.mjebooksdk.database.ReadLocation;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    List<ReadLocation> data;

    public BookmarkAdapter(List<ReadLocation> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
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

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(ReadLocation readLocation) {

        }
    }
}
