package ru.dmitrykuzmin.eroom.code;

import ru.dmitrykuzmin.eroom.common.model.enums.ActionType;

/**
 * Created by DmitryComp on 18.09.2014.
 */
public interface IDistrictObserver {
    void updateDistrict(String value, ActionType action);
}
