package org.archit.todomanagerservice.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.archit.todomanagerservice.entity.ToDoListStatus;

@Data
@Builder
public class ToDoListResponse {

    private Long id;
    private Date createdDate;
    private Date modifiedDate;
    private String name;
    private Date scheduledDate;
    private ToDoListStatus status;
    private List<ToDoItemDto> items;

}
