package com.mshdabiola.database.service

import com.mshdabiola.database.model.Users
import com.mshdabiola.model.ExposedUser
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class UserService(database: Database) {


    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }

    }

    suspend fun create(user: ExposedUser): Int = dbQuery {
        Users.insert {
            it[name] = user.name
            it[age] = user.age
        }[Users.id]
    }

    suspend fun read(id: Int): ExposedUser? {
        return dbQuery {
            Users.selectAll()
                .where { Users.id eq id }
                .map { ExposedUser(it[Users.name], it[Users.age]) }
                .singleOrNull()
        }
    }

    suspend fun update(id: Int, user: ExposedUser) {
        dbQuery {
            Users.update({ Users.id eq id }) {
                it[name] = user.name
                it[age] = user.age
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Users.deleteWhere { Users.id.eq(id) }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}