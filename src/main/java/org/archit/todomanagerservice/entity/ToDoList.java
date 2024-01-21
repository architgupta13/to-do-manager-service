package org.archit.todomanagerservice.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "to_do_list")
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "modified_date")
    private Date modifiedDate;
    private String name;
    @Column(name = "scheduled_date")
    private Date scheduledDate;
    @Enumerated(EnumType.STRING)
    private ToDoListStatus status = ToDoListStatus.CREATED;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ToDoItem> items;

}

