package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.awt.Color
import java.util.*

const val STATUS_CREATED_EVENT = "STATUS_CREATED_EVENT"

@DomainEvent(name = STATUS_CREATED_EVENT)
class StatusCreatedEvent(
        val projectId: UUID,
        val statusId: UUID,
        val statusName: String,
        val order: Int,
        val color: Color,
        createdAt: Long = System.currentTimeMillis(),
) : Event<StatusAggregate>(
        name = STATUS_CREATED_EVENT,
        createdAt = createdAt,
)