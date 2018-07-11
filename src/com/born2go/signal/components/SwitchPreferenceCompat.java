package com.born2go.signal.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.preference.CheckBoxPreference;
import android.util.AttributeSet;

import com.born2go.signal.R;

public class SwitchPreferenceCompat extends CheckBoxPreference {

    private OnPreferenceClickListener listener;

    public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutRes();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwitchPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayoutRes();
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutRes();
    }

    public SwitchPreferenceCompat(Context context) {
        super(context);
        setLayoutRes();
    }

    private void setLayoutRes() {
        setWidgetLayoutResource(R.layout.switch_compat_preference);
    }

    @Override
    public void setOnPreferenceClickListener(OnPreferenceClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onClick() {
        if (listener == null || !listener.onPreferenceClick(this)) {
            super.onClick();
        }
    }
}
