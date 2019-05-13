package com.mjebooksdk.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.mjebooksdk.R;
import com.mjebooksdk.ui.callbacks.IUpdateBookmarkDialogListner;

public class UpdateBookmarkDialog extends NoTitleDialog implements View.OnClickListener {

    private Button btnOk, btnCancel;
    private EditText edtAddTitle;
    private IUpdateBookmarkDialogListner updateListner;

    public UpdateBookmarkDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews() {
        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        edtAddTitle = findViewById(R.id.edtAddTitle);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.update_bookmark_dialog;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnOK) {
            if (updateListner != null) {
                updateListner.updateBookmark();
                dismiss();
            }
        } else if (id == R.id.btnCancel) {
            dismiss();
        }
    }

    public void setUpdateListner(IUpdateBookmarkDialogListner updateListner) {
        this.updateListner = updateListner;
    }

    public String getBookmarkTitle() {
        return edtAddTitle.getText().toString();
    }
}
