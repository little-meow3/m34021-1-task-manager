package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getAccount(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/tasks/{taskName}")
    fun createTask(@PathVariable projectId: UUID, @PathVariable taskName: String) : TaskCreatedEvent {
        return projectEsService.update(projectId) {
            it.addTask(taskName)
        }
    }

    @PostMapping("/{projectId}/{taskId}/{newName}")
    fun changeTaskName(@PathVariable projectId: UUID, @PathVariable taskId:UUID, @PathVariable newName: String) :
            TaskNameChangedEvent {
        return projectEsService.update(projectId) {
            it.changeTaskName(taskId, newName)
        }
    }

    @PostMapping("/{projectId}/{taskId}/status")
    fun assignStatusToTask(@PathVariable projectId: UUID, @PathVariable taskId:UUID, @RequestParam statusId: UUID) :
            StatusAssignedToTaskEvent {
        return projectEsService.update(projectId) {
            it.assignStatusToTask(taskId, statusId)
        }
    }

    @PostMapping("/{projectId}/users/{userId}")
    fun addUserToProject(@PathVariable projectId: UUID, @PathVariable userId:UUID) : UserAddedToProjectEvent {
        val user = userEsService.getState(userId)
                ?: throw IllegalArgumentException("There is no user: $userId")
        return projectEsService.update(projectId) {
            it.addUserToProject(userId, user.nickName, user.userName)
        }
    }

    @PostMapping("/{projectId}/{taskId}/users")
    fun addUserToProject(@PathVariable projectId: UUID, @PathVariable taskId:UUID, @RequestParam userId: UUID) :
            UserAssignedToTaskEvent {
        val user = userEsService.getState(userId)
                ?: throw IllegalArgumentException("There is no user: $userId")
        return projectEsService.update(projectId) {
            it.assignUserToTask(userId, taskId)
        }
    }
}