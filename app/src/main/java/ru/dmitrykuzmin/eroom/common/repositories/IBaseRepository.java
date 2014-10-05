package ru.dmitrykuzmin.eroom.common.repositories;

import java.util.List;

/**
 * Created by DmitryComp on 13.09.2014.
 */
public interface IBaseRepository<T> {
    List<T> getAll();
}
