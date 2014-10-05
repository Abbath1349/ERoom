package ru.dmitrykuzmin.eroom.parser;

import java.util.List;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public class TestDataProvider implements IDataProvider {

    IApartmentFilter filter;

    @Inject
    public TestDataProvider(IApartmentFilter filter) {
        this.filter = filter;
    }

    @Override
    public IApartmentFilter getFilter() {
        return filter;
    }

    @Override
    public List<IApartmentBase> parseApartments() {
        return null;
    }
}
