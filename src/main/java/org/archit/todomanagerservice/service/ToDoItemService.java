package org.archit.todomanagerservice.service;

import org.archit.todomanagerservice.entity.ToDoItem;

public interface ToDoItemService {

    ToDoItem createOrUpdateTodoItem(ToDoItem todoItem);

}
