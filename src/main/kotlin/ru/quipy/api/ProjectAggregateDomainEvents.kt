package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.awt.Color
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val TASK_NAME_CHANGED = "TASK_NAME_CHANGED"
const val STATUS_ASSIGNED_TO_TASK_EVENT = "STATUS_ASSIGNED_TO_TASK_EVENT"
const val USER_ADDED_TO_PROJECT_EVENT = "USER_ADDED_TO_PROJECT_EVENT"
//const val USER_ASSIGNED_TO_TASK_EVENT = "USER_ASSIGNED_TO_TASK_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val taskName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_NAME_CHANGED)
class TaskNameChangedEvent(
        val projectId: UUID,
        val taskId: UUID,
        val newTaskName: String,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = TASK_NAME_CHANGED,
        createdAt = createdAt
)

@DomainEvent(name = STATUS_CREATED_EVENT)
class StatusAddedEvent(
        val projectId: UUID,
        val statusId: UUID,
        val statusName: String,
        val order: Int,
        val color: Color,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = STATUS_CREATED_EVENT,
        createdAt = createdAt,
)

@DomainEvent(name = STATUS_ASSIGNED_TO_TASK_EVENT)
class StatusAssignedToTaskEvent(
    val projectId: UUID,
    val taskId: UUID,
    val statusId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_ASSIGNED_TO_TASK_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = USER_ADDED_TO_PROJECT_EVENT)
class UserAddedToProjectEvent(
        val projectId: UUID,
        val userId: UUID,
        val nickName:String,
        val userName:String,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = USER_ADDED_TO_PROJECT_EVENT,
        createdAt = createdAt
)

//@DomainEvent(name = USER_ASSIGNED_TO_TASK_EVENT)
//class UserAssignedToTaskEvent(
//        val projectId: UUID,
//        val taskId: UUID,
//        val userId: UUID,
//        createdAt: Long = System.currentTimeMillis(),
//) : Event<ProjectAggregate>(
//        name = USER_ASSIGNED_TO_TASK_EVENT,
//        createdAt = createdAt
//)