package controllers;

import controllers.api.PageController;
import models.entity.TodoItem;
import models.entity.TodoList;
import models.manage.TodoListManage;
import notifiers.Notifier;
import play.data.validation.Required;
import play.data.validation.Validation;

import java.util.Date;
import java.util.List;

/**
 * 待办 Action.
 *
 * @author itang
 */
public class Lists extends PageController {

    public static void index() {
        List<TodoList> lists = TodoListManage.instance(currUser().email).getAll("-createAt");

        render(lists);
    }

    public static void show(Long id) {
        TodoList list = getTodoListById(id);
        notFoundIfNull(list);
        checkOwner(list);
        List<TodoItem> items = list.items();
        List<TodoItem> oldItems = list.oldItems();
        render(list, items, oldItems);
    }

    public static void blank() {
        render();
    }

    public static void create(@Required String name) {
        if (Validation.hasErrors()) {
            flash.error("Oops, please give a name to your new list");
            blank();
        }
        new TodoList(currUser().email, name).insert();
        index();
    }

    public static void delete(Long id) {
        TodoList list = getTodoListById(id);
        notFoundIfNull(list);
        checkOwner(list);
        list.delete();
        flash.success("The list %s has been deleted", list);
        index();
    }

    public static void edit(Long id) {
        TodoList list = getTodoListById(id);
        notFoundIfNull(list);
        checkOwner(list);
        render(list);
    }

    public static void save(Long id, @Required String name, String notes) {
        if (Validation.hasErrors()) {
            params.flash();
            flash.error("Oops, please give a name to your list");
            edit(id);
        }
        TodoList list = getTodoListById(id);
        notFoundIfNull(list);
        checkOwner(list);
        list.name = name;
        list.notes = notes;
        list.update();
        show(list.id);
    }

    public static void addItem(Long id, String label) {
        TodoList list = getTodoListById(id);

        notFoundIfNull(list);
        checkOwner(list);

        TodoItem todoItem = new TodoItem(list, label);
        todoItem.insert();
        validation.valid(todoItem);
        if (validation.hasErrors()) {
            flash.error(validation.toString());
        } else {
            list.update(); // to keep last position up to date
        }

        show(id);
    }

    public static void changeItemState(Long id, Long itemId, boolean done) {
        TodoItem item = TodoItem.findById(itemId);
        notFoundIfNull(item);
        checkOwner(item);
        if (done) {
            item.closeAt = new Date();
        }
        item.done = done;
        item.position = item.list.nextPosition++;
        item.update();

        item.list.update();

        ok();
    }

    public static void deleteItem(Long id, Long itemId) {
        TodoItem item = TodoItem.findById(itemId);
        notFoundIfNull(item);
        checkOwner(item);
        item.delete();
        ok();
    }

    public static void reorderItems(Long id, String newOrder) {
        TodoList list = getTodoListById(id);
        notFoundIfNull(list);
        checkOwner(list);
        list.nextPosition = 0;
        for (String p : newOrder.split(",")) {
            TodoItem item = TodoItem.findById(Long.parseLong(p));
            if (item.list.id.equals(id)) {
                item.position = list.nextPosition++;
                item.update();
            }
        }
        list.update();

        ok();
    }

    public static void email(Long id) {
        TodoList list = getTodoListById(id);
        notFoundIfNull(list);
        checkOwner(list);
        Notifier.emailList(list);
        flash.success("This list has been emailed to %s", list.user);
        show(id);
    }

    private static TodoList getTodoListById(Long id) {
        return TodoListManage.instance(currUser().email).get(id);
    }

}

