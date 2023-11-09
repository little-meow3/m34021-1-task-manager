package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
//import ru.quipy.api.TagAssignedToTaskEvent
//import ru.quipy.api.TagCreatedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class ProjectEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "some-meaningful-name") {

            `when`(TaskCreatedEvent::class) { event ->
                logger.info("Task created: {}", event.taskName)
            }

            `when`(TaskNameChangedEvent::class) { event ->
                logger.info("Task name changed: {}", event.newTaskName)
            }

            `when`(StatusAddedEvent::class) { event ->
                logger.info("Status added: {}", event.statusName)
            }

            `when`(StatusAssignedToTaskEvent::class) { event ->
                logger.info("Status {} assigned to Task {}", event.statusId, event.taskId)
            }
        }
    }
}