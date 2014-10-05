package ru.dmitrykuzmin.eroom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Collection;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.MainActivity;
import ru.dmitrykuzmin.eroom.R;
import ru.dmitrykuzmin.eroom.code.IDistrictObservable;
import ru.dmitrykuzmin.eroom.code.IDistrictObserver;
import ru.dmitrykuzmin.eroom.common.model.enums.ActionType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.utils.CollectionUtils;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;

/**
 * Created by DmitryComp on 17.09.2014.
 */
public class SelectDistrictDialiog extends DialogFragment implements IDistrictObserver {

    @Inject
    IApartmentFilter filter;
    @Inject
    IDistrictObservable districtObservable;
    private boolean[] selectedItems;
    private String[] values;
    private DialogInterface.OnMultiChoiceClickListener itemClick = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            ActionType action = isChecked ? ActionType.ADD : ActionType.REMOVE;
            districtObservable.notifyObservers(values[which], action);
        }
    };
    private MainActivity activity;
    private Dialog dialog;
    private DialogInterface.OnClickListener okClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismiss();
        }
    };

    public static SelectDistrictDialiog create() {
        return new SelectDistrictDialiog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        districtObservable.registerObserver(this);
        activity = (MainActivity) getActivity();
        setData();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.select_district)
                    .setMultiChoiceItems(values
                            , selectedItems, itemClick)
                    .setPositiveButton(getString(R.string.ok), okClick);
            dialog = builder.create();
        }
        return dialog;
    }

    private void setData() {
        Collection<String> values = activity.getDistrictValues().values();
        int len = values.size();
        selectedItems = new boolean[len];
        this.values = values.toArray(new String[values.size()]);
    }

    @Override
    public void updateDistrict(String value, ActionType action) {
        DistrictType key = CollectionUtils.getKeyByValue(activity.getDistrictValues(), value);
        if (action == ActionType.ADD)
            filter.addDistrictType(key);
        else if (action == ActionType.REMOVE)
            filter.removeDistrictType(key);
    }

}
