package ru.dmitrykuzmin.eroom.common.builders;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.parser.IDataProvider;

/**
 * Created by DmitryComp on 13.09.2014.
 */
public class ApartmentRepoBuilder implements IApartmentRepoBuilder {

    private IDataProvider dataProvider;

    @Inject
    public ApartmentRepoBuilder(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public IDataProvider getDataProvider() {
        return dataProvider;
    }
}
