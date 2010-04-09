package controllers;

import controllers.api.PageController;
import models.api.Page;
import models.entity.Blog;
import models.entity.User;
import models.manage.BlogManage;
import org.apache.commons.lang.StringUtils;
import play.data.validation.Required;
import play.data.validation.Validation;

import java.util.List;

/**
 * 博客 Action.
 *
 * @author itang
 */
public class Blogs extends PageController {

    public static void index(int page) {
        Page<Blog> blogs = BlogManage.instance.pagedPublishedBlogs(page);
        render(blogs);
    }

    public static void blank() {
        render();
    }

    public static void create(@Required String title, String content) {
        if (Validation.hasErrors()) {
            flash.error("请输入标题");
            blank();
        }
        //发布还是暂存?
        final String actionType = params.get("actionType");
        if ("暂存".equals(actionType)) {
            BlogManage.instance(currUsername()).saveTempBlog(title, content);
            flash.success("成功暂存博客!");
            user_temps();
        } else {
            if (StringUtils.isBlank(content)) {
                flash.error("请输入博客内容!");
                blank();
            }
            BlogManage.instance(currUsername()).publishBlog(title, content);
            flash.success("成功发布博客!");
            renderUserhome();
        }
    }

    protected static void renderUserhome() {
        user(currUsername(), Page.DEFAULT_PAGE_START_INDEX);
    }

    public static void show(@Required Long id) {
        Blog blog = BlogManage.instance.get(id);

        notFoundIfNull(blog);
        //增加点击量
        if (!blog.username.equals(currUsername())) { //非blog作者
            BlogManage.instance.increaseHits(blog);
        }

        render(blog);
    }

    public static void user_temps() {
        List<Blog> blogs = BlogManage.instance(currUsername()).getUnpublishedBlogs();
        render(blogs);
    }

    public static void edit(@Required Long id) {
        Blog blog = BlogManage.instance(currUsername()).get(id);
        render(blog);
    }

    public static void delete(@Required Long id) {
        Blog blog = BlogManage.instance(currUsername()).get(id);
        if (blog == null) {
            flash.error("博客不存在!");
            renderUserhome();
        }
        BlogManage.instance(currUsername()).delete(id);
        flash.success(String.format("成功删除博客:%s", blog.title));
        renderUserhome();
    }

    public static void save(@Required Long id, @Required String title, String content) {
        if (Validation.hasErrors()) {
            flash.error("请输入标题");
            edit(id);
        }
        if (StringUtils.isBlank(content)) {
            flash.error("请输入博客内容!");
            edit(id);
        }

        final String actionType = params.get("actionType");
        Blog blog = BlogManage.instance(currUsername()).get(id);
        if (blog == null) {
            flash.error("博客不存在!");
            renderUserhome();
        }

        //保存修改内容
        BlogManage.instance.updateBlog(blog, title, content);

        if ("发布".equals(actionType)) {//同时发布博客
            BlogManage.instance.publishBlog(blog);

            flash.success("成功发布博客!");
            renderUserhome();
        } else {
            flash.success("成功保存博客!");
            edit(id);
        }
    }


    public static void user(@Required String user, int page) {
        User theUser = User.get(user);
        notFoundIfNull(theUser);

        Page<Blog> blogs = BlogManage.instance(user).pagedPublishedBlogs(page);
        renderArgs.put("user_name", user);
        renderArgs.put("user_nickname", theUser.nickname);
        render(blogs);
    }

}
