package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.awt.Color
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var projectTitle: String
    lateinit var creatorId: String
    var tasks = mutableMapOf<UUID, TaskEntity>()
    var projectStatuses = mutableMapOf<UUID, StatusEntity>()
    var participants = mutableMapOf<UUID, UserEntity>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        creatorId = event.creatorId
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        tasks[event.taskId] = TaskEntity(event.taskId, event.taskName, null, mutableSetOf())
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun taskNameChangedApply(event: TaskNameChangedEvent) {
        val task = tasks[event.taskId]
        tasks[event.taskId] = TaskEntity(event.taskId, event.newTaskName, task?.statusId, task?.usersAssigned)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun statusCreatedApply(event: StatusAddedEvent) {
        projectStatuses[event.statusId] =
                StatusEntity(event.statusId, event.name, event.order, event.color)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun statusAssignedToTakApply(event: StatusAssignedToTaskEvent) {
        val task = tasks[event.taskId]
        tasks[event.taskId] = TaskEntity(event.taskId, task?.name ?: "", event.statusId, task?.usersAssigned)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun userAddedApply(event: UserAddedToProjectEvent) {
        participants[event.userId] = UserEntity(event.userId, event.nickName, event.userName)
        updatedAt = createdAt
    }
}

data class TaskEntity(
    val id: UUID,
    val name: String,
    val statusId: UUID?,
    val usersAssigned: MutableSet<UUID>?
)

data class StatusEntity(
    val id: UUID,
    val name: String,
    val order: Int,
    val color: Color
)

data class UserEntity(
        val id: UUID,
        val nickName: String,
        val userName: String
)

/**
 * Demonstrates that the transition functions might be representer by "extension" functions, not only class members functions
 */
//@StateTransitionFunc
//fun ProjectAggregateState.tagAssignedApply(event: TagAssignedToTaskEvent) {
//    tasks[event.taskId]?.tagsAssigned?.add(event.tagId)
//        ?: throw IllegalArgumentException("No such task: ${event.taskId}")
//    updatedAt = createdAt
//}
