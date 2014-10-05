package ru.dmitrykuzmin.eroom.parser;

import android.content.Context;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;
import ru.dmitrykuzmin.eroom.common.model.ILink;
import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;
import ru.dmitrykuzmin.eroom.common.utils.ResUtils;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public abstract class Parser {
    public static int COUNT_OF_PAGES_FOR_PARSE = 1;
    Context context;
    private IApartmentFilter filter;
    private String parserPrefix;
    private Map<RealEstateType, String> realEstateValues;
    private Map<ApartmentType, String> apartmentValues;
    private Map<DistrictType, String> districtValues;
    private Map<PeriodType, String> periodValues;
    private String baseLink;

    public Parser(IApartmentFilter filter, Context context) {
        this.filter = filter;
        this.context = context;
    }

    public IApartmentFilter getFilter() {
        return filter;
    }

    protected String getBaseLink() {
        if (baseLink == null) {
            baseLink = ResUtils.getStringByName(context,
                    String.format("%s_base_link", getParserPrefix()));
        }
        return baseLink;
    }

    public String getParserPrefix() {
        if (parserPrefix == null) {
            String className = getClass().getSimpleName();
            parserPrefix = className.replace("Parser", "").toLowerCase();
        }
        return parserPrefix;
    }

    public abstract ILink buildLink();

    public Map<RealEstateType, String> getRealEstateValues() {
        if (realEstateValues == null)
            realEstateValues = loadEnumValues(RealEstateType.class);
        return realEstateValues;
    }

    protected Map<ApartmentType, String> getApartmentValues() {
        if (apartmentValues == null)
            apartmentValues = loadEnumValues(ApartmentType.class);
        return apartmentValues;
    }

    protected Map<DistrictType, String> getDistrictValues() {
        if (districtValues == null)
            districtValues = loadEnumValues(DistrictType.class);
        return districtValues;
    }

    protected Map<PeriodType, String> getPeriodValues() {
        if (periodValues == null)
            periodValues = loadEnumValues(PeriodType.class);
        return periodValues;
    }

    private <T extends Enum<T>> Map<T, String> loadEnumValues(Class<T> enumClass) {
        Map<T, String> map = new EnumMap<T, String>(enumClass);
        for (T t : enumClass.getEnumConstants()) {
            map.put(t, ResUtils.getStringByName(context, String.format("%s_%s", getParserPrefix(),
                    t.toString().toLowerCase())));
        }
        return map;
    }

    public abstract List<IApartmentBase> parse();
}
