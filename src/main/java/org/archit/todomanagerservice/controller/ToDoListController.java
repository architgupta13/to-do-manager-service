package org.archit.todomanagerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.archit.todomanagerservice.model.ToDoListRequest;
import org.archit.todomanagerservice.model.ToDoListResponse;
import org.archit.todomanagerservice.service.ToDoListService;
import org.archit.todomanagerservice.util.PaginationUtil;
import org.archit.todomanagerservice.util.ToDoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "TO-DO Manager", description = "TO-DO Manager APIs")
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/api/todos")
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;

    @Operation(summary = "Gets all to-do lists by page request")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ToDoListResponse>> getAllToDoLists(@RequestParam final int page,
        @RequestParam final int size) {
        return ResponseEntity.ok(
            this.toDoListService.getToDoLists(PaginationUtil.withLimitAndDerivedSort(page, size))
                .stream()
                .map(ToDoUtil::toToDoListResponse)
                .toList());
    }

    @Operation(summary = "Gets a to-do list by id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ToDoListResponse> getToDoListById(@PathVariable final Long id) {
        return ResponseEntity.ok(this.toDoListService.getToDoList(id)
            .map(ToDoUtil::toToDoListResponse)
            .orElse(null));
    }

    @Operation(summary = "Creates a to-do list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ToDoListResponse> createToDoList(
        @RequestBody final ToDoListRequest request) {
        return Optional.ofNullable(this.toDoListService.createToDoList(request))
            .map(ToDoUtil::toToDoListResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Updates a to-do list")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ToDoListResponse> updateToDoList(@PathVariable final Long id,
        @RequestBody final ToDoListRequest request) {
        return Optional.ofNullable(this.toDoListService.updateToDoList(id, request))
            .map(ToDoUtil::toToDoListResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Deletes a to-do list by id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteToDoList(@PathVariable final Long id) {
        this.toDoListService.deleteToDoList(Collections.singletonList(id));
        return ResponseEntity.accepted().build();
    }
}
