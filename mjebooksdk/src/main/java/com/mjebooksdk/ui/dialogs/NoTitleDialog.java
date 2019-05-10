package com.mjebooksdk.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import androidx.annotation.NonNull;

public abstract class NoTitleDialog extends Dialog {

    public NoTitleDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(getLayoutId());
        initViews();
    }

    protected abstract void initViews();
    protected abstract int getLayoutId();
}
