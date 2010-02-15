package models.api;

import siena.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Page.
 *
 * @author itang
 */
public class Page<M extends Model> implements Iterable<M> {
     public static final int DEFAULT_PAGE_START_INDEX = 1;
    public static final int DEFAULT_LIMIT = 10;
    public static final Integer DEFAULT_START = 0;
    public final int total;
    public final List<M> items;
    public final int start;
    public final int limit;
    public final int currPage;
    public final int totalPage;

    public Page(int total, List<M> items, int start, int limit) {
        this.total = total;
        this.items = (items == null ? new ArrayList<M>(0) : items);
        this.start = start;
        this.limit = limit;
        this.currPage = start / limit + 1;
        this.totalPage = total / limit + 1;
    }

    public boolean isFirstPage() {
        return currPage == 1;
    }

    public boolean isLastPage() {
        return currPage == totalPage;
    }

    public Iterator<M> iterator() {
        return items.iterator();
    }

    public String toString() {
        return new StringBuilder("Page{")
                .append("total:").append(total)
                .append(",result(size:").append(items.size()).append(")[...]")
                .append(",start:").append(start)
                .append(",limit:").append(limit)
                .append(",currPage:").append(currPage)
                .append(",totalPage:").append(totalPage)
                .append("}").toString();
    }

}
