package utils;

import org.w3c.dom.Document;
import play.exceptions.UnexpectedException;
import play.libs.XML;
import play.mvc.Http.Request;
import play.mvc.Http.Response;

/**
 * RSS render 支持.
 *
 * @author itang
 */
public class RenderRss extends play.mvc.results.Result {

    private final String xml;

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
