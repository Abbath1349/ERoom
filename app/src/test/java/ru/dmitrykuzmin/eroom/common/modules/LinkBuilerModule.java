package ru.dmitrykuzmin.eroom.common.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.Parser;
import ru.dmitrykuzmin.eroom.parser.TestE1Parser;
import ru.dmitrykuzmin.eroom.parserTests.LinkBuilderTest;
import ru.dmitrykuzmin.eroom.parserTests.ParserTest;

/**
 * Created by DmitryComp on 07.09.2014.
 */
@Module(
        complete = false,
        injects = {LinkBuilderTest.class, ParserTest.class}
)
public class LinkBuilerModule {
    @Provides
    public Parser provideParser(IApartmentFilter filter,Context context){
        return new TestE1Parser(filter,context);
    }
}
