package org.archit.todomanagerservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.archit.todomanagerservice.entity.ToDoList;
import org.archit.todomanagerservice.model.ToDoListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToDoListService {

    Optional<ToDoList> getToDoList(long id);

    List<ToDoList> getToDoLists(Collection<Long> ids);

    Page<ToDoList> getToDoLists(Pageable pageable);

    ToDoList createToDoList(ToDoListRequest request);

    ToDoList updateToDoList(long id, ToDoListRequest request);

    void deleteToDoList(Collection<Long> ids);

}
