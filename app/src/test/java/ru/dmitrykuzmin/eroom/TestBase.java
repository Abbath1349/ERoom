package ru.dmitrykuzmin.eroom;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import dagger.ObjectGraph;
import ru.dmitrykuzmin.eroom.common.modules.LinkBuilerModule;
import ru.dmitrykuzmin.eroom.common.modules.TestParserModule;

/**
 * Created by DmitryComp on 04.09.2014.
 */

@RunWith(RobolectricTestRunner.class)
public class TestBase {
    private ObjectGraph objectGraph;

    @Before
    public void setUp() throws Exception {
       Object[] modules = new Object[]{
                new TestParserModule(Robolectric.application),new LinkBuilerModule()
        };
        objectGraph = ObjectGraph.create(modules);
        objectGraph.inject(this);
    }
}
