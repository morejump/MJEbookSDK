package com.mjebooksdk.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.mjebooksdk.R;
import com.mjebooksdk.ui.callbacks.IDeleteBookmarkDialogListener;

public class DeleteBookmarkDialog extends NoTitleDialog implements View.OnClickListener {

    private IDeleteBookmarkDialogListener deleteBookmarkDialogListener;
    private Button btnDelete, btnCancel;

    public DeleteBookmarkDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initViews() {
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.delete_bookmark_dialog;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCancel) {
            dismiss();
        } else if (id == R.id.btnDelete) {
            deleteBookmarkDialogListener.deleteBookmark();
            dismiss();
        }
    }

    public void setDeleteListener(IDeleteBookmarkDialogListener deleteBookmarkDialogListener){
        this.deleteBookmarkDialogListener = deleteBookmarkDialogListener;
    }
}
