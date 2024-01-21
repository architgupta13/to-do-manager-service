package org.archit.todomanagerservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.archit.todomanagerservice.entity.ToDoListStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToDoListRequest {

    private String name;
    private Date scheduledDate;
    private ToDoListStatus status;
    private List<ToDoItemDto> items;
}
