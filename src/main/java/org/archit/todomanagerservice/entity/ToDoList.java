package org.archit.todomanagerservice.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "modified_date")
    private Date modifiedDate;
    private String name;
    @Column(name = "scheduled_date")
    private Date scheduledDate;
    @Enumerated(EnumType.STRING)
    private ToDoListStatus status;
    @OneToMany(mappedBy = "toDoList", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<ToDoItem> items = new ArrayList<>();

}

