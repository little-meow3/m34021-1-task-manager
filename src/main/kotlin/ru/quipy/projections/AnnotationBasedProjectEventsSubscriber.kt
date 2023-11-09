package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.*
//import ru.quipy.api.TagCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "demo-subs-stream"
)
class AnnotationBasedProjectEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedProjectEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: TaskCreatedEvent) {
        logger.info("Task created: {}", event.taskName)
    }

    @SubscribeEvent
    fun taskNameChangedSubscriber(event: TaskNameChangedEvent) {
        logger.info("Task name changed: {}", event.newTaskName)
    }

    @SubscribeEvent
    fun statusCreatedSubscriber(event: StatusAddedEvent) {
        logger.info("Status added: {}", event.statusName)
    }

    @SubscribeEvent
    fun statusAssignedToTaskSubscriber(event: StatusAssignedToTaskEvent) {
        logger.info("Status {} assigned to Task {}", event.statusId, event.taskId)
    }

    @SubscribeEvent
    fun userAddedSubscriber(event: UserAddedToProjectEvent) {
        logger.info("User added: {}", event.userId)
    }
}