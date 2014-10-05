package ru.dmitrykuzmin.eroom.common.utils;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by DmitryComp on 20.09.2014.
 */
public class ButterKnifeUtils {
    public static ButterKnife.Setter<View,Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    public static ButterKnife.Action<View> DISABLED = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setEnabled(true);
        }
    };
}
