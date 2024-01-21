package org.archit.todomanagerservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoItemDto {
    private int order;
    private String description;
    private boolean completed;
}
