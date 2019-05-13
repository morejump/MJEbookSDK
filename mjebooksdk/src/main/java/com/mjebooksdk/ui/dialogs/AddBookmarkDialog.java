package com.mjebooksdk.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.mjebooksdk.R;
import com.mjebooksdk.ui.callbacks.IAddBookmarkDialogListener;

public class AddBookmarkDialog extends NoTitleDialog implements View.OnClickListener {

    private IAddBookmarkDialogListener addBookmarkListener;
    private Button btnOK, btnCancel;
    private EditText edtAddTitle;
    private String bookmarkTitle;

    public AddBookmarkDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initViews() {
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        edtAddTitle = findViewById(R.id.edtAddTitle);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_bookmark_dialog;
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.btnOK) {
            if (addBookmarkListener != null) {
                addBookmarkListener.addBookmark();
            }
            dismiss();
        } else if (i == R.id.btnCancel) {
            dismiss();
        }
    }

    public void setAddBookmarkListener(IAddBookmarkDialogListener addBookmarkListener) {
        this.addBookmarkListener = addBookmarkListener;
    }

    public String getBookmarkTitle() {
        return edtAddTitle.getText().toString();
    }

}
