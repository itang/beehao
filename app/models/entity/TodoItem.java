package models.entity;

import models.api.Ownerable;
import siena.*;

import java.util.Date;

public class TodoItem extends Model implements Ownerable<String> {
    @Id
    public Long id;
    //名称
    public String label;
    //是否完成
    public boolean done;
    //位置
    public int position;
    //所属待办列表
    @Index("list_index")
    public TodoList list;
    //创建时间
    public Date createAt;
    //完成时间
    public Date closeAt;

    public TodoItem(TodoList list, String label) {
        this.label = label;
        this.list = list;
        this.position = list.nextPosition++;
        this.createAt = new Date();
    }

    static Query<TodoItem> all() {
        return Model.all(TodoItem.class);
    }

    public static TodoItem findById(Long id) {
        return all().filter("id", id).get();
    }

    @Override
    public String toString() {
        return label;
    }

    public String owner() {
        list.get();
        return list.owner();
    }
}

