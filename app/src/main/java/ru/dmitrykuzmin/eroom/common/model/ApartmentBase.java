package ru.dmitrykuzmin.eroom.common.model;


public class ApartmentBase extends BaseEntity implements IApartmentBase {
    private String detailsLink;
    private String district;
    private Integer price;
    private String address;

    public String getAddress() {
        if (address == null)
            return "-";
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        if (district == null)
            return "-";
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
