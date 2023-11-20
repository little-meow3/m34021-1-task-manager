package ru.quipy.logic

import ru.quipy.api.StatusAggregate
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.awt.Color
import java.util.*

class StatusAggregateState : AggregateState<UUID, StatusAggregate> {
    private lateinit var statusId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var statusName: String
    lateinit var projectId: UUID
    lateinit var orderNumber: Number
    lateinit var color: Color
    override fun getId() = statusId

    @StateTransitionFunc
    fun statusCreatedApply(event: StatusCreatedEvent) {
        projectId = event.projectId
        statusId = event.statusId
        statusName = event.statusName
        orderNumber = event.order
        color = event.color
        updatedAt = createdAt
    }
}