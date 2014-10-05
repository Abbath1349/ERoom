package ru.dmitrykuzmin.eroom.parserTests;

import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.ShadowLog;

import java.lang.reflect.Method;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.TestBase;
import ru.dmitrykuzmin.eroom.common.model.ILink;
import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;
import ru.dmitrykuzmin.eroom.common.utils.CollectionUtils;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.Parser;

/**
 * Created by DmitryComp on 07.09.2014.
 */

public class LinkBuilderTest extends TestBase{
    /**values for test links*/
    public static final int priceLow=10000;
    public static final int priceHigh=30000;
    public static final int SQARE_MIN=43;
    public static final int SQARE_MAX=55;


    public static final String E1_LINK_ROOM=
            "http://arenda.e1.ru/snyat/rooms-komnata/srok-dlitelniy/?gorod=ekaterinburg&form=3&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_THREE_ROOM_NO_PAGE =
            "http://arenda.e1.ru/snyat/rooms-2,3,4/srok-dlitelniy/?gorod=ekaterinburg&form=7&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_THREE_ROOM_PAGE_TWO =
            "http://arenda.e1.ru/snyat/rooms-2,3,4/srok-dlitelniy/?gorod=ekaterinburg&form=7&by=_orderDate&order=DESC&on_page=25&page=2";
    public static final String E1_LINK_PRICE_FROM=
            "http://arenda.e1.ru/snyat/rooms-1,2,3,4,5,6/srok-dlitelniy/?gorod=ekaterinburg&form=7&cena-ot=10000&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_PRICE_TO=
            "http://arenda.e1.ru/snyat/rooms-1,2,3,4,5,6/srok-dlitelniy/?gorod=ekaterinburg&form=7&cena-do=30000&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_PRICE_BOTH=
            "http://arenda.e1.ru/snyat/rooms-1,2,3,4,5,6/srok-dlitelniy/?gorod=ekaterinburg&form=7&cena-ot=10000&cena-do=30000&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_DISTRICT_VYZ=
            "http://arenda.e1.ru/snyat/rayon-viz/rooms-1,2,3,4,5,6/srok-dlitelniy/?gorod=ekaterinburg&form=7&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_UNIVERSAL_DISTRICTS=""; //???
    public static final String E1_LINK_PERIOD_LONG =
            "http://arenda.e1.ru/snyat/rooms-1,2,3,4,5,6/srok-dlitelniy/?gorod=ekaterinburg&form=7&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_PERIOD_DAY=
            "http://arenda.e1.ru/snyat/rooms-1,2,3,4,5,6/srok-posutochno/?gorod=ekaterinburg&form=7&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_PERIOD_HOUR=
            "http://arenda.e1.ru/snyat/rooms-1,2,3,4,5,6/srok-chasi/?gorod=ekaterinburg&form=7&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_SQUARE=
            "http://arenda.e1.ru/snyat/rooms-komnata/srok-dlitelniy/?gorod=ekaterinburg&form=3&obshchaya-ploshchad-ot=43&obshchaya-ploshchad-do=55&by=_orderDate&order=DESC&on_page=25";
    public static final String E1_LINK_ALL_PARAMS=
            "http://arenda.e1.ru/snyat/rayon-viz,vokzal'ny'j,vtorchermet/rooms-2,3/srok-dlitelniy/?gorod=ekaterinburg&form=7&cena-ot=10000&cena-do=30000&by=_orderDate&order=DESC&on_page=25";


    @Inject
    Parser e1Parser;
    @Inject
    IApartmentFilter filter;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        ShadowLog.stream = System.out;
    }

    @Test
    public void testGetBaseLink(){
        ShadowLog.d("test","testGetBAseLink");
        Log.d("test","testGetBAseLink");
        try {
            Class cls = e1Parser.getClass();
            Method method = cls.getDeclaredMethod("getBaseLink");
            method.setAccessible(true);
            Assert.assertEquals((String)method.invoke(e1Parser), "http://arenda.e1.ru/snyat");
        } catch (Exception e){
            Assert.assertTrue("This is exception "+e.getMessage(),Boolean.FALSE);
        }
    }

    @Test
    public void testE1Room(){
      //  filter.addAppartmentType(ApartmentType.ROOM);
        filter.setRealEstateType(RealEstateType.ROOM);
        filter.setPeriod(PeriodType.LONG);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_ROOM);
    }

    @Test
    public void testThreeRoomNoPage(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(new ApartmentType[]{
                ApartmentType.APARTMENT_FOUR, ApartmentType.APARTMENT_TWO,
                ApartmentType.APARTMENT_THREE
        }));
        filter.setPeriod(PeriodType.LONG);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_THREE_ROOM_NO_PAGE);
    }

    @Test
    public void testThreeRoomPageTwo(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(new ApartmentType[]{
                ApartmentType.APARTMENT_FOUR, ApartmentType.APARTMENT_TWO,
                ApartmentType.APARTMENT_THREE
        }));
        filter.setPeriod(PeriodType.LONG);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(2), E1_LINK_THREE_ROOM_PAGE_TWO);
    }

    @Test
    public void testE1PriceLow(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(ApartmentType.values()));
       // filter.removeAppartmentType(ApartmentType.ROOM);
        filter.setPriceLow(priceLow);
        filter.setPeriod(PeriodType.LONG);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_PRICE_FROM);
    }

    @Test
    public void testE1PriceHigh(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(ApartmentType.values()));
       // filter.removeAppartmentType(ApartmentType.ROOM);
        filter.setPriceHigh(priceHigh);
        filter.setPeriod(PeriodType.LONG);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_PRICE_TO);
    }

    @Test
    public void testE1PriceBoth(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(ApartmentType.values()));
       // filter.removeAppartmentType(ApartmentType.ROOM);
        filter.setPriceHigh(priceHigh);
        filter.setPriceLow(priceLow);
        filter.setPeriod(PeriodType.LONG);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_PRICE_BOTH);
    }

    @Test
    public void testE1DistrictVyz(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(ApartmentType.values()));
       // filter.removeAppartmentType(ApartmentType.ROOM);
        filter.setPeriod(PeriodType.LONG);
        filter.addDistrictType(DistrictType.VIZ);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_DISTRICT_VYZ);
    }

    @Test
    public void testE1LongPeriod(){
        filter.setPeriod(PeriodType.LONG);
        ILink link=e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null), E1_LINK_PERIOD_LONG);
    }
    @Test
    public void testE1DayPeriod(){
        filter.setPeriod(PeriodType.DAY);
        ILink link=e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_PERIOD_DAY);
    }
    @Test
    public void testE1LongHour(){
        filter.setPeriod(PeriodType.HOUR);
        ILink link=e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null), E1_LINK_PERIOD_HOUR);
    }

    @Test
    public void testLinkSqare(){
        //filter.addAppartmentType(ApartmentType.ROOM);
        filter.setRealEstateType(RealEstateType.ROOM);
        filter.setPeriod(PeriodType.LONG);
        filter.setSquareMin(SQARE_MIN);
        filter.setSquareMax(SQARE_MAX);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_SQUARE);
    }

    @Test
    public void testAllParams(){
        filter.setApartmentTypes(CollectionUtils.CreateSortedSet(new ApartmentType[]{
                ApartmentType.APARTMENT_TWO, ApartmentType.APARTMENT_THREE
        }));
        filter.setPeriod(PeriodType.LONG);
        filter.setDistrictTypes(CollectionUtils.CreateSortedSet(new DistrictType[]{
                DistrictType.VIZ, DistrictType.VOKZALNIY, DistrictType.VTORCHERMET
        }));
        filter.setPriceLow(priceLow);
        filter.setPriceHigh(priceHigh);
        ILink link = e1Parser.buildLink();
        filter.clean();
        Assert.assertEquals(link.getLink(null),E1_LINK_ALL_PARAMS);
    }
}
