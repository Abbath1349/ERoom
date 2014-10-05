package ru.dmitrykuzmin.eroom.common.repositories;

import java.util.List;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.common.builders.IApartmentRepoBuilder;
import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;
import ru.dmitrykuzmin.eroom.parser.IDataProvider;

/**
 * Created by DmitryComp on 14.09.2014.
 */
public class ApartmentRepository implements IApartmentRepository {

    public IDataProvider getDataProvider() {
        return dataProvider;
    }

    //private IApartmentRepoBuilder builder;
    private IDataProvider dataProvider;

    @Inject
    public ApartmentRepository(IApartmentRepoBuilder builder) {
        //this.builder = builder;
        dataProvider = builder.getDataProvider();
    }

    @Override
    public List<IApartmentBase> getAll() {
        return getDataProvider().parseApartments();
    }
}
