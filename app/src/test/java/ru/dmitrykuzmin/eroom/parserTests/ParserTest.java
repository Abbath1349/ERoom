package ru.dmitrykuzmin.eroom.parserTests;

import junit.framework.Assert;

import org.junit.Test;

import javax.inject.Inject;

import ru.dmitrykuzmin.eroom.TestBase;
import ru.dmitrykuzmin.eroom.parser.Parser;

/**
 * Created by DmitryComp on 07.09.2014.
 */
public final class ParserTest extends TestBase {

    @Inject
    Parser e1Parser;

    @Test
    public void testInjectsWorks(){
        Assert.assertTrue(e1Parser!=null);
    }

    @Test
    public void testPrefix() {
        /*try {
            Method method = E1Parser.class.getDeclaredMethod("getParserPrefix",null);
            method.setAccessible(true);
            Assert.assertTrue(method.invoke(e1Parser,null).toString()=="e1");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage(),Boolean.FALSE);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage(),Boolean.FALSE);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage(),Boolean.FALSE);
        }
*/
        Assert.assertTrue(e1Parser.getParserPrefix().equals("teste1"));
    }
/*
    @Test
    public void testParseOk(){
        List<IAppartmentBase> appartments= e1Parser.parse();
        Assert.assertTrue(appartments==null);
        // parse and apprtment base has 300 values
    }*/
}
