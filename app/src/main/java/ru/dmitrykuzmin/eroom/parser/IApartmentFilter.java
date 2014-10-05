package ru.dmitrykuzmin.eroom.parser;

import java.util.Set;

import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public interface IApartmentFilter {
    void clean();

    Integer getPriceLow();

    void setPriceLow(Integer priceLow);

    Integer getPriceHigh();

    void setPriceHigh(Integer priceHigh);

    Set<ApartmentType> getApartmentTypes();

    void setApartmentTypes(Set<ApartmentType> apartmentTypes);

    void addAppartmentType(ApartmentType apartmentType);

    void removeAppartmentType(ApartmentType apartmentType);

    Set<DistrictType> getDistrictTypes();

    void setDistrictTypes(Set<DistrictType> districtTypes);

    void addDistrictType(DistrictType districtType);

    void removeDistrictType(DistrictType districtType);

    void setPeriod(PeriodType type);

    PeriodType getPeriodType();

    Integer getSquareMin();

    void setSquareMin(Integer squareMin);

    Integer getSquareMax();

    void setSquareMax(Integer squareMax);

    RealEstateType getRealEstateType();

    void setRealEstateType(RealEstateType realEstateType);

}
