package ru.dmitrykuzmin.eroom.code;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrykuzmin.eroom.common.model.enums.ActionType;

/**
 * Created by DmitryComp on 18.09.2014.
 */
public class DistrictObservable implements IDistrictObservable {
    private List<IDistrictObserver> observers;

    public List<IDistrictObserver> getObservers() {
        if (observers == null)
            observers = new ArrayList<IDistrictObserver>();
        return observers;
    }

    @Override
    public void registerObserver(IDistrictObserver observer) {
        if (!getObservers().contains(observer)) {
            getObservers().add(observer);
        }
    }

    @Override
    public void removeObserver(IDistrictObserver observer) {
        if (getObservers().contains(observer)) {
            getObservers().remove(observer);
        }
    }

    @Override
    public void notifyObservers(String value, ActionType action) {
        for (IDistrictObserver o : observers) {
            o.updateDistrict(value, action);
        }
    }
}
