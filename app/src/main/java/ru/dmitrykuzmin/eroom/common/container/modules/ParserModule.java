package ru.dmitrykuzmin.eroom.common.container.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.dmitrykuzmin.eroom.ApartmentListActivity;
import ru.dmitrykuzmin.eroom.MainActivity;
import ru.dmitrykuzmin.eroom.code.DistrictObservable;
import ru.dmitrykuzmin.eroom.code.IDistrictObservable;
import ru.dmitrykuzmin.eroom.common.builders.ApartmentRepoBuilder;
import ru.dmitrykuzmin.eroom.common.builders.IApartmentRepoBuilder;
import ru.dmitrykuzmin.eroom.common.repositories.ApartmentRepository;
import ru.dmitrykuzmin.eroom.common.repositories.IApartmentRepository;
import ru.dmitrykuzmin.eroom.common.tasks.ApartmentBaseLoader;
import ru.dmitrykuzmin.eroom.dialogs.SelectDistrictDialiog;
import ru.dmitrykuzmin.eroom.parser.ApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.DataProvider;
import ru.dmitrykuzmin.eroom.parser.E1Parser;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.IDataProvider;
import ru.dmitrykuzmin.eroom.parser.Parser;

/**
 * Created by DmitryComp on 04.09.2014.
 */
@Module(
        library = true,
        injects = {MainActivity.class,IDataProvider.class,E1Parser.class,
                IApartmentRepoBuilder.class, IApartmentRepository.class,ApartmentBaseLoader.class,
                SelectDistrictDialiog.class, ApartmentListActivity.class}
)
public class ParserModule {
    private final Context context;

    public ParserModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides @Singleton
    IApartmentFilter provideAppartMentFilter(){
        return new ApartmentFilter();
    }

    @Provides
    public IDataProvider provideDataProvider(IApartmentFilter filter,Parser parser){
        return new DataProvider(filter,parser);
    }

    @Provides// @Named(E1Parser.NAME)
    public Parser provideE1Parser(IApartmentFilter filter){
        return new E1Parser(filter,context);
    }

    @Provides @Singleton
    public IApartmentRepoBuilder provideRepoBuilder(IDataProvider dataProvider){
        return new ApartmentRepoBuilder(dataProvider);
    }

    @Provides @Singleton
    public IApartmentRepository provideApartmentRepository(IApartmentRepoBuilder builder){
        return new ApartmentRepository(builder);
    }

    @Provides @Singleton
    public IDistrictObservable provideDistrictObservable(){
        return new DistrictObservable();
    }
}
