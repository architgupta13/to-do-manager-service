package org.archit.todomanagerservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "to_do_item")
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int order;
    private String description;
    private boolean completed;

}
