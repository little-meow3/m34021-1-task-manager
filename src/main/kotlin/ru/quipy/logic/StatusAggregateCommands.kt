package ru.quipy.logic

import ru.quipy.api.StatusCreatedEvent
import java.awt.Color
import java.util.*

fun StatusAggregateState.create(projectId: UUID, statusId: UUID, statusName: String, orderNumber: Int, color: Color)
: StatusCreatedEvent {
    return StatusCreatedEvent(
            projectId = projectId,
            statusId = statusId,
            statusName = statusName,
            order = orderNumber,
            color = color,
    )
}