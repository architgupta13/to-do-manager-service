package org.archit.todomanagerservice.service.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.archit.todomanagerservice.entity.ToDoItem;
import org.archit.todomanagerservice.repository.ToDoItemRepository;
import org.archit.todomanagerservice.service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ToDoItemServiceImpl implements ToDoItemService {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    public ToDoItem createOrUpdateTodoItem(final ToDoItem todoItem) {
        return Optional.ofNullable(todoItem).map(this.toDoItemRepository::save).orElse(null);
    }

}
