package ru.dmitrykuzmin.eroom.parser;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public class ApartmentFilter implements IApartmentFilter {
    private Integer priceLow;
    private Integer priceHigh;
    private RealEstateType realEstateType;
    private Set<ApartmentType> apartmentTypes;
    private PeriodType periodType;
    private Set<DistrictType> districtTypes;
    private Integer squareMin;
    private Integer squareMax;

    public Integer getSquareMin() {
        return squareMin;
    }

    public void setSquareMin(Integer squareMin) {
        this.squareMin = squareMin;
    }

    public Integer getSquareMax() {
        return squareMax;
    }

    public void setSquareMax(Integer squareMax) {
        this.squareMax = squareMax;
    }

    @Override
    public RealEstateType getRealEstateType() {
        return realEstateType;
    }

    @Override
    public void setRealEstateType(RealEstateType realEstateType) {
        this.realEstateType = realEstateType;
    }

    @Override
    public void clean() {
        Field[] fields = ApartmentFilter.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Class<?> type = field.getType();
            try {
                if (type.isPrimitive())
                    field.set(this, 0);
                else if (!type.isEnum())
                    field.set(this, null);

            } catch (IllegalAccessException e) {

            }
        }
    }

    @Override
    public Integer getPriceLow() {
        return priceLow;
    }

    @Override
    public void setPriceLow(Integer priceLow) {
        this.priceLow = priceLow;
    }

    @Override
    public Integer getPriceHigh() {
        return priceHigh;
    }

    @Override
    public void setPriceHigh(Integer priceHigh) {
        this.priceHigh = priceHigh;
    }

    @Override
    public Set<ApartmentType> getApartmentTypes() {
        if (apartmentTypes == null)
            apartmentTypes = new HashSet<ApartmentType>();
        return apartmentTypes;
    }

    @Override
    public void setApartmentTypes(Set<ApartmentType> apartmentTypes) {
        this.apartmentTypes = apartmentTypes;
    }

    @Override
    public void addAppartmentType(ApartmentType apartmentType) {
        getApartmentTypes().add(apartmentType);
    }

    @Override
    public void removeAppartmentType(ApartmentType apartmentType) {
        if (getApartmentTypes().size() == 0) {
            return;
        }
        getApartmentTypes().remove(apartmentType);
    }

    @Override
    public Set<DistrictType> getDistrictTypes() {
        if (districtTypes == null) {
            districtTypes = new HashSet<DistrictType>();
        }
        return districtTypes;
    }

    @Override
    public void setDistrictTypes(Set<DistrictType> districtTypes) {
        this.districtTypes = districtTypes;
    }

    @Override
    public void addDistrictType(DistrictType districtType) {
        getDistrictTypes().add(districtType);
    }

    @Override
    public void removeDistrictType(DistrictType districtType) {
        if (getDistrictTypes().size() == 0) {
            return;
        }
        getDistrictTypes().remove(districtType);
    }

    @Override
    public void setPeriod(PeriodType type) {
        this.periodType = type;
    }

    @Override
    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }
}
