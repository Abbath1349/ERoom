package ru.dmitrykuzmin.eroom.common.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by DmitryComp on 08.09.2014.
 */
public class CollectionUtils {
    public static <T> Set<T> CreateHashSet(T[] array){
        Set<T> set=new HashSet<T>();
        for(T v : array){
            set.add(v);
        }
        return set;
    }

    public static <T> Set<T> CreateSortedSet(T[] array){
        Set<T> set = new TreeSet<T>();
        for(T v : array){
            set.add(v);
        }
        return set;
    }

    public static <K,V> K getKeyByValue(Map<K,V> map,V value){
        for(Map.Entry<K,V> e : map.entrySet()){
            if(e.getValue().equals(value))
                return e.getKey();
        }
        return null;
    }
}
