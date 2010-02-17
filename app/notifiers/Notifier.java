package notifiers;

import models.entity.TodoList;
import play.mvc.Mailer;

public class Notifier extends Mailer {

    public static void emailList(TodoList list) {
        setFrom(list.username);
        setSubject("Your list: %s", list.name);
        addRecipient(list.username);
        send(list);
    }

}

