package ru.dmitrykuzmin.eroom.common.model;

import java.util.Date;

/**
 * Created by DmitryComp on 22.09.2014.
 */
public interface IApartmentDetails {
    public String getCity();

    public void setCity(String city);

    public String getFloor();

    public void setFloor(String floor);

    public Date getPublicationDate();

    public void setPublicationDate(Date publicationDate);

    public String getSquare();

    public void setSquare(String square);

    public String getNumberOfRooms();

    public void setNumberOfRooms();
}
