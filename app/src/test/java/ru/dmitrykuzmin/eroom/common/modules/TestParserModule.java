package ru.dmitrykuzmin.eroom.common.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.dmitrykuzmin.eroom.parser.ApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.IDataProvider;
import ru.dmitrykuzmin.eroom.parser.TestDataProvider;
import ru.dmitrykuzmin.eroom.parser.TestE1Parser;
import ru.dmitrykuzmin.eroom.parserTests.AppartmentFilterTest;

/**
 * Created by DmitryComp on 04.09.2014.
 */

@Module
        (
                injects = {AppartmentFilterTest.class,
                        TestDataProvider.class, IDataProvider.class, TestE1Parser.class},
                // includes = ParserModule.class,
                overrides = true
        )
public class TestParserModule {
    private final Context context;

    public TestParserModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public IApartmentFilter provideApplicationFilter() {
        return new ApartmentFilter();
    }

    @Provides
    public IDataProvider provideDataProvider(IApartmentFilter filter) {
        return new TestDataProvider(filter);
    }

}
