package org.archit.todomanagerservice.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.archit.todomanagerservice.entity.ToDoList;
import org.archit.todomanagerservice.model.ToDoListRequest;
import org.archit.todomanagerservice.repository.ToDoListRepository;
import org.archit.todomanagerservice.service.ToDoItemService;
import org.archit.todomanagerservice.service.ToDoListService;
import org.archit.todomanagerservice.util.ToDoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private ToDoItemService toDoItemService;

    @Override
    public Optional<ToDoList> getToDoList(final long id) {
        return this.toDoListRepository.findById(id);
    }

    @Override
    public List<ToDoList> getToDoLists(final Collection<Long> ids) {
        return this.toDoListRepository.findAllById(ids);
    }

    @Override
    public Page<ToDoList> getToDoLists(final Pageable pageable) {
        return this.toDoListRepository.findAll(pageable);
    }

    @Override
    public ToDoList createToDoList(final ToDoListRequest request) {
        val toDoList = ToDoUtil.createToDoList(request);
        Optional.ofNullable(toDoList.getItems())
            .filter(Predicate.not(CollectionUtils::isEmpty))
            .stream()
            .flatMap(List::stream)
            .peek(item -> item.setToDoList(toDoList))
            .forEach(this.toDoItemService::createOrUpdateTodoItem);
        return this.toDoListRepository.save(toDoList);
    }

    @Override
    public ToDoList updateToDoList(final long id, final ToDoListRequest request) {
        val toDoListInDb = getToDoList(id).orElseThrow(
            () -> new RuntimeException("To-do List with specified id not found"));
        ToDoUtil.updateToDoList(toDoListInDb, request);
        Optional.ofNullable(toDoListInDb.getItems())
            .filter(Predicate.not(CollectionUtils::isEmpty))
            .stream()
            .flatMap(List::stream)
            .peek(item -> item.setToDoList(toDoListInDb))
            .forEach(this.toDoItemService::createOrUpdateTodoItem);
        return this.toDoListRepository.save(toDoListInDb);
    }

    @Override
    public void deleteToDoList(final Collection<Long> ids) {
        this.toDoListRepository.deleteAllById(ids);
    }
}
