package ru.dmitrykuzmin.eroom.parserTests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.Integer;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.TestBase;
import ru.dmitrykuzmin.eroom.TestERoomApplication;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;
import ru.dmitrykuzmin.eroom.common.modules.TestParserModule;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;
import ru.dmitrykuzmin.eroom.parser.IDataProvider;
import ru.dmitrykuzmin.eroom.parser.TestDataProvider;

/**
 * Created by DmitryComp on 04.09.2014.
 */
//@RunWith(RobolectricTestRunner.class)
public class AppartmentFilterTest extends TestBase {
    private static Integer PRICE_LOW=10000;
    private static int PRICE_HIGH=30000;
    private static int SQUARE_MAX =100;
    private static int SQUARE_MIN =30;


    @Inject
    IApartmentFilter appartmentFilter;
    @Inject
    IDataProvider dataProvider;

    @Test
    public void testInjectWorks() {
        Assert.assertTrue(appartmentFilter != null);
        Assert.assertTrue(dataProvider != null);
        Assert.assertTrue(dataProvider.getFilter()!=null);
    }

    @Test
    public void testSingletonWorks() {
        IApartmentFilter filter = dataProvider.getFilter();
        Assert.assertTrue(filter!=null);
        filter.setPriceLow(PRICE_LOW);
        Assert.assertEquals(appartmentFilter.getPriceLow(),PRICE_LOW);
        appartmentFilter.clean();
        Assert.assertNull(appartmentFilter.getPriceLow());
        Assert.assertNull(filter.getPriceLow());
    }

    @Test
    public void testSingletonFilterNoLosesData() {
        IApartmentFilter filter = dataProvider.getFilter();
        Assert.assertTrue(filter!=null);
        filter.setPriceLow(PRICE_LOW);
        Assert.assertEquals(appartmentFilter.getPriceLow(),PRICE_LOW);
    }

    @Test
    public void testCleanWorks(){
        IApartmentFilter filter = dataProvider.getFilter();
        filter.setPriceLow(PRICE_LOW);
        filter.setPriceLow(PRICE_HIGH);
        filter.setSquareMin(SQUARE_MIN);
        filter.setSquareMax(SQUARE_MAX);
        PeriodType periodType = filter.getPeriodType();
        RealEstateType realEstateType = filter.getRealEstateType();
        filter.clean();
        Assert.assertEquals(0, filter.getApartmentTypes().size());
        Assert.assertEquals(0, filter.getDistrictTypes().size());
        Assert.assertEquals(periodType, filter.getPeriodType());
        Assert.assertEquals(realEstateType, filter.getRealEstateType());
        Integer nullInt=null;
        Assert.assertEquals(nullInt, filter.getPriceLow());
        Assert.assertEquals(nullInt, filter.getPriceHigh());
        Assert.assertEquals(nullInt, filter.getSquareMax());
        Assert.assertEquals(nullInt, filter.getSquareMin());
    }
}
