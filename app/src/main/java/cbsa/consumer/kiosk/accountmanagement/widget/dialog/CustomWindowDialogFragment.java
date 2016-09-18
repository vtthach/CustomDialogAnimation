package cbsa.consumer.kiosk.accountmanagement.widget.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import consumer.kiosk.R;


public abstract class CustomWindowDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setGravity(getGravity());
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setWindowAnimations(getAnimationStyle());
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Default dialog layout param will wrap_content
        // So just reset it when fragment start to show when onStart()
        Window window = getDialog().getWindow();
        window.setLayout(getWindowWidth(), getWindowHeight());
    }

    protected int getAnimationStyle() {
        return R.style.AccountRightDialogAnim;
    }

    protected abstract int getLayoutId();

    protected abstract int getWindowHeight();

    protected abstract int getWindowWidth();

    protected abstract int getGravity();


}

