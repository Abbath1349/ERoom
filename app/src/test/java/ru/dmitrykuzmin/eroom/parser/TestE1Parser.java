package ru.dmitrykuzmin.eroom.parser;

import android.content.Context;

import java.util.EnumMap;
import java.util.Map;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;

/**
 * Created by DmitryComp on 09.09.2014.
 */
public class TestE1Parser extends E1Parser {
    private Map<RealEstateType, String> realEstateValues;
    private Map<ApartmentType, String> apartmentValues;
    private Map<DistrictType, String> districtValues;
    private Map<PeriodType, String> periodValues;

    @Inject
    public TestE1Parser(IApartmentFilter filter, Context context) {
        super(filter, context);
    }

    @Override
    protected String getBaseLink() {
      /*  Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("www.myawesomesite.com").
                appendPath("turtles").
                appendPath("types").appendQueryParameter("type", "1").appendQueryParameter("sort", "relevance");

        return builder.build().toString();
        */
        return "http://arenda.e1.ru/snyat";
    }

    @Override
    public Map<RealEstateType, String> getRealEstateValues() {
        if (realEstateValues == null || realEstateValues.size() == 0) {
            realEstateValues = new EnumMap<RealEstateType, String>(RealEstateType.class);
            realEstateValues.put(RealEstateType.ROOM, "komnata");
            realEstateValues.put(RealEstateType.APARTMENT, "");
        }
        return realEstateValues;
    }

    @Override
    protected Map<ApartmentType, String> getApartmentValues() {
        if (apartmentValues == null || apartmentValues.size() == 0) {
            apartmentValues = new EnumMap<ApartmentType, String>(ApartmentType.class);
            apartmentValues.put(ApartmentType.APARTMENT_ONE, "1");
            apartmentValues.put(ApartmentType.APARTMENT_TWO, "2");
            apartmentValues.put(ApartmentType.APARTMENT_THREE, "3");
            apartmentValues.put(ApartmentType.APARTMENT_FOUR, "4");
            apartmentValues.put(ApartmentType.APARTMENT_FIVE_AND_MORE, "5,6");
        }
        return apartmentValues;
    }

    @Override
    protected Map<DistrictType, String> getDistrictValues() {
        if (districtValues == null || districtValues.size() == 0) {
            districtValues = new EnumMap<DistrictType, String>(DistrictType.class);
            districtValues.put(DistrictType.VIZ, "viz");
            districtValues.put(DistrictType.VOKZALNIY, "vokzal'ny'j");
            districtValues.put(DistrictType.VTORCHERMET, "vtorchermet");
        }
        return districtValues;
    }

    @Override
    protected Map<PeriodType, String> getPeriodValues() {
        if (periodValues == null || periodValues.size() == 0) {
            periodValues = new EnumMap<PeriodType, String>(PeriodType.class);
            periodValues.put(PeriodType.HOUR, "chasi");
            periodValues.put(PeriodType.DAY, "posutochno");
            periodValues.put(PeriodType.LONG, "dlitelniy");
        }
        return periodValues;
    }
}
