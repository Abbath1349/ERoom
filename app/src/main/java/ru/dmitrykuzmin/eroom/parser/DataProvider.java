package ru.dmitrykuzmin.eroom.parser;

import java.util.List;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public class DataProvider implements IDataProvider {

    public Parser getE1Parser() {
        return e1Parser;
    }

    IApartmentFilter filter;
    //@Inject @Named(E1Parser.NAME)
    Parser e1Parser;

    @Inject
    public DataProvider(IApartmentFilter filter, Parser parser) {
        this.filter = filter;
        this.e1Parser = parser;
    }

    @Override
    public IApartmentFilter getFilter() {
        return filter;
    }

    @Override
    public List<IApartmentBase> parseApartments() {
        return e1Parser.parse();
    }
}
