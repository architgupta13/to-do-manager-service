package org.archit.todomanagerservice.repository;

import org.archit.todomanagerservice.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

}
