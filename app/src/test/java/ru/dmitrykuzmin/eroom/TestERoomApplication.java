package ru.dmitrykuzmin.eroom;

import org.robolectric.Robolectric;

import java.util.List;

import ru.dmitrykuzmin.eroom.common.modules.TestParserModule;

/**
 * Created by DmitryComp on 05.09.2014.
 */
public class TestERoomApplication extends ERoomApplication {
    @Override
    protected List<Object> getModules() {
        List<Object> modules = super.getModules();
        modules.add(new TestParserModule(this));
        return modules;
    }

    public static <T> T injectMocks(T object){
        //TestERoomApplication app = (TestERoomApplication) Robolectric.application;
        ERoomApplication app = ERoomApplication.from(Robolectric.application);
        return app.inject(object);
    }
}
