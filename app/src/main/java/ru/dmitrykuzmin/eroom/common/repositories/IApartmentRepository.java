package ru.dmitrykuzmin.eroom.common.repositories;

import java.util.List;

import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;

/**
 * Created by DmitryComp on 13.09.2014.
 */
public interface IApartmentRepository{
    List<IApartmentBase> getAll();
}
