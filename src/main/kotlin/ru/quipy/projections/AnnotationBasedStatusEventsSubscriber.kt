package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.StatusAggregate
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
        aggregateClass = StatusAggregate::class, subscriberName = "demo-subs-stream1"
)
class AnnotationBasedStatusEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedStatusEventsSubscriber::class.java)

    @SubscribeEvent
    fun statusCreatedSubscriber(event: StatusCreatedEvent) {
        logger.info("Status created: {}", event.statusName)
    }
}