package models.manage;

import com.google.appengine.api.datastore.Blob;
import models.api.Ownerable;
import models.api.OwnerableManage;
import models.api.Page;
import models.entity.Blog;
import play.data.validation.Required;
import play.data.validation.Validation;
import siena.Query;

import java.util.List;

/**
 * BlogManage.
 *
 * @author itang
 */
public class BlogManage extends OwnerableManage<Blog> implements Ownerable<String> {
    public static BlogManage instance = new BlogManage(null);
    public final String owner;

    public BlogManage(String owner) {
        this.owner = owner;
    }

    public String owner() {
        return this.owner;
    }

    public Query<Blog> publishedBlogsQuery(String order) {
        final Query<Blog> q = query().filter("status", Blog.STATUS_PUBLISHED);
        return order == null ? q.order("-createAt") : q.order(order);
    }

    public List<Blog> getPublishedBlogs() {
        return publishedBlogsQuery(null).fetch();
    }


    public List<Blog> getPublishedBlogs(String order) {
        return publishedBlogsQuery(order).fetch();
    }

    public List<Blog> getUnpublishedBlogs() {
        return query().filter("status", Blog.STATUS_UNPUBLISHED).order("-createAt").fetch();
    }

    public Page<Blog> pagedPublishedBlogs(int currPage) {
        return pagedPublishedBlogs(null, currPage, Page.DEFAULT_LIMIT);
    }

    public Page<Blog> pagedPublishedBlogs(String order, int currPage, int limit) {
        return page(publishedBlogsQuery(order), currPage, limit);
    }

    public Blog publishBlog(String title, String content) {
        return saveBlog(title, content, Blog.STATUS_PUBLISHED);
    }

    public Blog publishBlog(Blog blog) {
        blog.status = Blog.STATUS_PUBLISHED;
        blog.update();
        return blog;
    }

    public Blog saveTempBlog(String title, String content) {
        return saveBlog(title, content, Blog.STATUS_UNPUBLISHED);
    }

    public Blog saveBlog(String title, String content, int status) {
        Blog blog = new Blog(this.owner(), title, new Blob(content.getBytes()));
        blog.author = currUser().nickname;
        blog.status = status;
        blog.insert();
        return blog;
    }

    public static BlogManage instance(String owner) {
        return new BlogManage(owner);
    }


}
