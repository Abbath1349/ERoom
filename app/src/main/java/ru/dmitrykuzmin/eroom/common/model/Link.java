package ru.dmitrykuzmin.eroom.common.model;

import ru.dmitrykuzmin.eroom.code.Names;

/**
 * Created by DmitryComp on 08.09.2014.
 */
public class Link implements ILink {

    private final String noPageUrl;
    private final String pageLink;

    private Link(String noPageUrl, String url) {
        this.noPageUrl = noPageUrl;
        this.pageLink = url;
    }

    public static Link create(String noPageUrl, String pageLink) {
        return new Link(noPageUrl, pageLink);
    }

    public static Link createNoPageLink(String noPageUrl) {
        return new Link(noPageUrl, null);
    }

    public static Link createPageLink(String pageLink) {
        return new Link(null, pageLink);
    }

    @Override
    public String getLink(Integer page){
        if (page == null && noPageUrl == null) {
            return null;
        }
        if (page == null) {
            return noPageUrl;
        }
        return String.format(pageLink, page);
    }
}
