package ru.dmitrykuzmin.eroom.common.model;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public class BaseEntity implements IBaseEntity {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
