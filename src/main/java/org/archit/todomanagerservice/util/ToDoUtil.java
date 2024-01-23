package org.archit.todomanagerservice.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.archit.todomanagerservice.entity.ToDoItem;
import org.archit.todomanagerservice.entity.ToDoList;
import org.archit.todomanagerservice.entity.ToDoListStatus;
import org.archit.todomanagerservice.entity.User;
import org.archit.todomanagerservice.model.ToDoItemDto;
import org.archit.todomanagerservice.model.ToDoListRequest;
import org.archit.todomanagerservice.model.ToDoListResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

public class ToDoUtil {

    public static ToDoListResponse toToDoListResponse(final ToDoList toDoList) {
        return ToDoListResponse.builder()
            .id(toDoList.getId())
            .createdDate(toDoList.getCreatedDate())
            .modifiedDate(toDoList.getModifiedDate())
            .name(toDoList.getName())
            .scheduledDate(toDoList.getScheduledDate())
            .status(toDoList.getStatus())
            .items(Optional.ofNullable(toDoList.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(ToDoUtil::toToDoItemDto)
                .sorted(Comparator.comparing(ToDoItemDto::getOrder))
                .toList())
            .build();
    }

    public static ToDoList createToDoList(final ToDoListRequest request) {
        val toDoList = new ToDoList();

        val now = new Date();
        toDoList.setCreatedDate(now);
        toDoList.setModifiedDate(now);
        val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        toDoList.setUsername(user.getUsername());

        Optional.ofNullable(request.getName())
            .ifPresentOrElse(toDoList::setName, () -> RandomStringUtils.randomAlphabetic(10));
        Optional.ofNullable(request.getItems())
            .map(ToDoUtil::toToDoItems)
            .ifPresent(toDoList::setItems);
        Optional.ofNullable(request.getStatus())
            .ifPresentOrElse(toDoList::setStatus, () -> toDoList.setStatus(ToDoListStatus.CREATED));

        return toDoList;
    }

    public static void updateToDoList(final ToDoList toDoListInDb, final ToDoListRequest request) {
        if (Objects.isNull(toDoListInDb) || Objects.isNull(request)) {
            return;
        }

        val now = new Date();
        toDoListInDb.setModifiedDate(now);

        Optional.ofNullable(request.getName()).ifPresent(toDoListInDb::setName);
        Optional.ofNullable(request.getScheduledDate()).ifPresent(toDoListInDb::setScheduledDate);
        Optional.ofNullable(request.getItems())
            .map(ToDoUtil::toToDoItems)
            .ifPresent(toDoListInDb::setItems);
        Optional.ofNullable(request.getStatus()).ifPresent(toDoListInDb::setStatus);
    }

    private static ToDoItemDto toToDoItemDto(final ToDoItem toDoItem) {
        return ToDoItemDto.builder()
            .order(toDoItem.getOrder())
            .description(toDoItem.getDescription())
            .completed(toDoItem.isCompleted())
            .build();
    }

    private static List<ToDoItem> toToDoItems(final List<ToDoItemDto> toDoItemDtos) {
        if (CollectionUtils.isEmpty(toDoItemDtos)) {
            return Collections.emptyList();
        }

        return toDoItemDtos.stream()
            .map(ToDoUtil::toToDoItem)
            .toList();
    }

    public static ToDoItem toToDoItem(final ToDoItemDto request) {
        val toDoItem = new ToDoItem();

        Optional.of(request.getId()).ifPresent(toDoItem::setId);
        Optional.of(request.getOrder()).ifPresent(toDoItem::setOrder);
        Optional.ofNullable(request.getDescription()).ifPresent(toDoItem::setDescription);
        toDoItem.setCompleted(request.isCompleted());

        return toDoItem;
    }

}
