package controllers;

import controllers.api.PageController;
import models.api.Page;
import models.entity.Blog;
import models.entity.User;
import models.manage.BlogManage;
import play.data.validation.Required;
import play.data.validation.Validation;

/**
 * 博客 Action.
 *
 * @author itang
 */
public class Blogs extends PageController {
    public static void index() {
        index_page(Page.DEFAULT_PAGE_START_INDEX);
    }

    public static void index_page(int currPage) {
        Page<Blog> blogs = BlogManage.instance.pagedPublishedBlogs(currPage);
        render(blogs);
    }

    public static void blank() {
        render();
    }

    public static void create(@Required String title, @Required String content) {
        if (Validation.hasErrors()) {
            flash.error("请输入标题或内容");
            blank();
        }

        flash.success("成功发布博客!");
        BlogManage.instance(currUsername()).addBlog(title, content);

        user(currUsername());
    }

    public static void show(@Required Long id) {
        render();
    }


    public static void user(@Required String user) {
        user_page(user, Page.DEFAULT_PAGE_START_INDEX);
    }

    public static void user_page(@Required String user, int currPage) {
        Page<Blog> blogs = BlogManage.instance(user).pagedPublishedBlogs(currPage);
        render(blogs);
    }
}
