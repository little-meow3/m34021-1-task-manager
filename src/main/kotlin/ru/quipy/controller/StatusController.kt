package ru.quipy.controller

import org.beryx.awt.color.ColorFactory
import org.springframework.web.bind.annotation.*
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.StatusAggregate
import ru.quipy.api.StatusCreatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.awt.Color
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("/statuses")
class StatusController(
        val statusEsService: EventSourcingService<UUID, StatusAggregate, StatusAggregateState>,
        val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
) {
    @PostMapping("/{statusName}")
    fun createStatus(@PathVariable statusName: String, @RequestParam projectId: UUID, @RequestParam color: String) :
            StatusCreatedEvent {
        val project = projectEsService.getState(projectId)
                ?: throw IllegalArgumentException("There is no project: $projectId")
        val statusInProject = project.projectStatuses.filter { it.value.name == statusName }
        if (statusInProject.isNotEmpty())
            throw IllegalArgumentException("Status with this name already exists: $statusName")
        val statusId = UUID.randomUUID()
        projectEsService.update(projectId) {
            it.addStatus(statusId, statusName, project.projectStatuses.size + 1, ColorFactory.valueOf(color))
        }
//        projectEsService.update(projectId, StatusCreatedEvent(projectId, statusId, statusName, ColorFactory.valueOf(color)))
//        project.statusCreatedApply(StatusEntity(statusId, statusName, 0, ColorFactory.valueOf(color)))

        return statusEsService.create {
            it.create(projectId, UUID.randomUUID(), statusName, project.projectStatuses.size + 1, ColorFactory.valueOf(color))
        }
    }

    @GetMapping("/{statusId}")
    fun getStatus(@PathVariable statusId: UUID) : StatusAggregateState? {
        return statusEsService.getState(statusId)
    }
}