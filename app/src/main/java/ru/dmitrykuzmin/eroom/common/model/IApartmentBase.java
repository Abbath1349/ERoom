package ru.dmitrykuzmin.eroom.common.model;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public interface IApartmentBase {
    String getDetailsLink();

    void setDetailsLink(String detailsLink);

    Integer getPrice();

    void setPrice(Integer price);

    String getAddress();

    void setAddress(String address);

    String getDistrict();

    void setDistrict(String district);

}
