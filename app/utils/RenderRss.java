package utils;

import org.w3c.dom.Document;
import play.exceptions.UnexpectedException;
import play.libs.XML;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Http.Response;

public class RenderRss extends play.mvc.results.Result {

    String xml;

    public RenderRss(CharSequence xml) {
        this.xml = xml.toString();
    }

    public RenderRss(Document document) {
        this.xml = XML.serialize(document);
    }

    public void apply(Request request, Response response) {
        try {
            setContentTypeIfNotSet(response, "application/rss+xml");
            response.out.write(xml.getBytes("utf-8"));
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
    }
}
