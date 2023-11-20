package ru.quipy.logic

import ru.quipy.api.StatusCreatedEvent
import ru.quipy.api.UserCreatedEvent
import java.awt.Color
import java.util.*

fun UserAggregateState.create(userId: UUID, nickName: String, userName: String): UserCreatedEvent {
    return UserCreatedEvent(userId = userId, nickName = nickName, userName = userName)
}