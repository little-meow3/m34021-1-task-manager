package ru.quipy.logic

import ru.quipy.api.*
//import ru.quipy.api.StatusCreatedEvent
//import ru.quipy.api.TagAssignedToTaskEvent
//import ru.quipy.api.TagCreatedEvent
import java.awt.Color
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.addTask(name: String): TaskCreatedEvent {
    return TaskCreatedEvent(projectId = this.getId(), taskId = UUID.randomUUID(), taskName = name)
}

fun ProjectAggregateState.changeTaskName(taskId: UUID, newName: String): TaskNameChangedEvent {
    if (!tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task doesn't exists: $taskId")
    }
    return TaskNameChangedEvent(projectId = this.getId(), taskId = taskId, newTaskName = newName)
}

fun ProjectAggregateState.addStatus(statusId: UUID, name: String, order:Int, color: Color): StatusAddedEvent {
    return StatusAddedEvent(projectId = this.getId(), statusId = statusId, statusName = name, order = order, color = color)
}

fun ProjectAggregateState.assignStatusToTask(taskId: UUID, statusId: UUID): StatusAssignedToTaskEvent {
    if (!tasks.containsKey(taskId)) {
        throw IllegalArgumentException("Task doesn't exists: $taskId")
    }
    if (!projectStatuses.containsKey(statusId)) {
        throw IllegalArgumentException("Status doesn't exists: $statusId")
    }
    return StatusAssignedToTaskEvent(projectId = this.getId(), taskId = taskId, statusId = statusId)
}

fun ProjectAggregateState.addUserToProject(userId: UUID, nickName: String, userName: String): UserAddedToProjectEvent {
    if (participants.contains(userId)) {
        throw IllegalArgumentException("User already in project: $userId")
    }
    return UserAddedToProjectEvent(projectId = this.getId(), userId = userId, nickName = nickName, userName =  userName)
}

fun ProjectAggregateState.assignUserToTask(userId: UUID, taskId: UUID): UserAssignedToTaskEvent {
    if (tasks.contains(userId)) {
        throw IllegalArgumentException("User already assigned: $userId")
    }
    return UserAssignedToTaskEvent(projectId = this.getId(), userId = userId, taskId = taskId)
}