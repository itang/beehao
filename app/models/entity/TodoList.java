package models.entity;

import models.api.Ownerable;
import siena.Filter;
import siena.Id;
import siena.Model;
import siena.Query;

import java.util.Date;
import java.util.List;

public class TodoList extends Model implements Ownerable<String> {
    @Id
    public Long id;
    //拥有者
    public String username;
    //名称
    public String name;
    //备注
    public String notes;
    //下一个位置
    public int nextPosition;
    //创建时间
    public Date createAt;
    //关闭时间
    public Date closeAt;
    //待办条目
    @Filter("list")
    public Query<TodoItem> items;

    public TodoList(String user) {
        this(user, null);
    }

    public TodoList(String user, String name) {
        this.username = user;
        this.name = name;
        this.notes = "";
        this.nextPosition = 0;
        this.createAt = new Date();
    }

    public List<TodoItem> items() {
        return items.filter("done", false).order("position").fetch();
    }

    public List<TodoItem> oldItems() {
        return items.filter("done", true).order("-position").fetch();
    }

    @Override
    public String toString() {
        return name;
    }

    public String owner() {
        return this.username;
    }
}

