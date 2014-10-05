package ru.dmitrykuzmin.eroom.parser;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.dmitrykuzmin.eroom.common.model.ApartmentBase;
import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;
import ru.dmitrykuzmin.eroom.common.model.ILink;
import ru.dmitrykuzmin.eroom.common.model.Link;
import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;
import ru.dmitrykuzmin.eroom.common.utils.CollectionUtils;

/**
 * Created by DmitryComp on 04.09.2014.
 */
public class E1Parser extends Parser {

    public static final String NAME = "e1 parser";
    public static String RESULT_ELEMENT = "tr.re-search-result-table__body-row";

    public E1Parser(IApartmentFilter filter, Context context) {
        super(filter, context);
    }

    @Override
    public ILink buildLink() {
        StringBuilder builder = new StringBuilder(getBaseLink());
        if (getFilter().getDistrictTypes().size() > 0) {
            builder.append(buildDistrictPart());
        }

        builder.append(buildApartmentPart());
        builder.append(String.format("/srok-%s", getPeriodValues().get(getFilter().getPeriodType())));
        builder.append("/?gorod=ekaterinburg");
        builder.append("&form=" + getFormNumber());
        setPriceToUrl(builder);
        setSquareToUrl(builder);
        setEnd(builder);

        String noPageUrl = builder.toString();

        return Link.create(noPageUrl, noPageUrl + "&page=%d");
    }

    @Override
    public List<IApartmentBase> parse() {
        List<IApartmentBase> list = new ArrayList<IApartmentBase>();
        ILink link = buildLink();
        for (int i = 1; i <= COUNT_OF_PAGES_FOR_PARSE; i++)
            list.addAll(parseAppartmentList(link.getLink(i)));
        return list;
    }

    public List<IApartmentBase> parseAppartmentList(String url) {
        List<IApartmentBase> appartments = new ArrayList<IApartmentBase>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Elements links = doc.select(RESULT_ELEMENT);

            for (Element link : links) {
                Elements room = link.children();
                List<String> roomSpecs = new ArrayList<String>();
                for (Element roomData : room) {
                    if (roomSpecs.size() == 0)
                        roomSpecs.add(roomData.select("a[href]").attr("href"));
                    roomSpecs.add(roomData.text());
                }
                appartments.add(buildAppartmentBase(roomSpecs));
            }
        } catch (IOException e) {


        } catch (Exception e) {
        } finally {
            return appartments;
        }
    }

    /**
     * sequense
     * 1. details link
     * 2. price
     * 3. type
     * 4. photo
     * 5. sqare
     * 6. floor
     * 7. address
     * 8. district
     * 9. city
     * 10. date
     */
    public IApartmentBase buildAppartmentBase(List<String> data) {
        if (data.size() != 10)
            return new ApartmentBase();
        else {
            IApartmentBase aBase = new ApartmentBase();
            aBase.setDetailsLink(data.get(0));
            aBase.setPrice(Integer.parseInt(data.get(1).replaceAll("[^\\d]", "")));
            aBase.setAddress(data.get(6));
            aBase.setDistrict(data.get(7));
            return aBase;
        }
    }

    private void setPriceToUrl(StringBuilder builder) {
        if (getFilter().getPriceLow() != null)
            builder.append("&cena-ot=" + getFilter().getPriceLow().toString());
        if (getFilter().getPriceHigh() != null)
            builder.append("&cena-do=" + getFilter().getPriceHigh().toString());
    }

    private void setSquareToUrl(StringBuilder builder) {
        if (getFilter().getRealEstateType() == RealEstateType.ROOM) {
            if (getFilter().getSquareMin() != null)
                builder.append("&obshchaya-ploshchad-ot=" + getFilter().getSquareMin().toString());
            if (getFilter().getSquareMax() != null)
                builder.append("&obshchaya-ploshchad-do=" + getFilter().getSquareMax().toString());
        }
    }

    private void setEnd(StringBuilder builder) {
        builder.append("&by=_orderDate");
        builder.append("&order=DESC");
        builder.append("&on_page=25");
    }

    private String buildDistrictPart() {
        StringBuilder builder = new StringBuilder("/rayon-");
        for (DistrictType d : getFilter().getDistrictTypes()) {
            builder.append(getDistrictValues().get(d));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private String buildApartmentPart() {
        StringBuilder builder = new StringBuilder("/rooms-");

        if (getFilter().getRealEstateType() == RealEstateType.ROOM) {
            builder.append(getRealEstateValues().get(getFilter().getRealEstateType()));
        } else {
            if (getFilter().getApartmentTypes().size() == 0) {
                getFilter().setApartmentTypes(CollectionUtils.CreateSortedSet(ApartmentType.values()));
            }
            for (ApartmentType a : getFilter().getApartmentTypes()) {
                builder.append(getApartmentValues().get(a));
                builder.append(',');
            }
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    private String getFormNumber() {
        if (getFilter().getRealEstateType() == RealEstateType.ROOM)
            return "3";
        return "7";
    }
}
