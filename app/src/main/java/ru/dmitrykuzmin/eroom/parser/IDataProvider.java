package ru.dmitrykuzmin.eroom.parser;

import java.util.List;

import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public interface IDataProvider {

    IApartmentFilter getFilter();
    List<IApartmentBase> parseApartments();
}
