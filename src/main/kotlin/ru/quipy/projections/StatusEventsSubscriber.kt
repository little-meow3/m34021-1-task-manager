package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.StatusAggregate
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.api.TaskCreatedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class StatusEventsSubscriber {
    val logger: Logger = LoggerFactory.getLogger(StatusEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(StatusAggregate::class, "some-meaningful-name1") {

            `when`(StatusCreatedEvent::class) { event ->
                logger.info("Status created: {}", event.statusName)
            }
        }
    }
}