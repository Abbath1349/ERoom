package ru.dmitrykuzmin.eroom.common.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;
import ru.dmitrykuzmin.eroom.common.repositories.IApartmentRepository;

/**
 * Created by DmitryComp on 14.09.2014.
    Class in ObjectGraph
 */
public class ApartmentBaseLoader extends AsyncTaskLoader<List<IApartmentBase>> {
    @Inject
    IApartmentRepository apartmentRepository;
    List<IApartmentBase> data;


    @Inject
    public ApartmentBaseLoader(Context context) {
        super(context);
    }

    @Override
    public List<IApartmentBase> loadInBackground() {
        return apartmentRepository.getAll();
    }

    @Override
    public void deliverResult(List<IApartmentBase> data) {
        this.data = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        if (data != null) {
            data.clear();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(List<IApartmentBase> data) {
        if(data!=null)
            data.clear();
    }
}
