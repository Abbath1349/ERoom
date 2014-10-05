package ru.dmitrykuzmin.eroom;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import ru.dmitrykuzmin.eroom.common.container.modules.ParserModule;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public class ERoomApplication extends Application {
    private ObjectGraph objectGraph;

    public static ERoomApplication from(Context context) {
        return (ERoomApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());

    }

    public <T> T inject(T object) {
        return objectGraph.inject(object);
    }

    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<Object>();
        modules.add(new ParserModule(this));
        return modules;
    }
}
