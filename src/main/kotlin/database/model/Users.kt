package com.mshdabiola.database.model

import org.jetbrains.exposed.v1.core.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)
    val age = integer("age")

    override val primaryKey = PrimaryKey(id)
}