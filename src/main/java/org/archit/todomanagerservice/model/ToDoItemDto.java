package org.archit.todomanagerservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoItemDto {

    private long id;
    private int order;
    private String description;
    private boolean completed;
}
