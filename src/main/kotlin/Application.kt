package com.mshdabiola

import com.mshdabiola.database.service.UserService
import com.mshdabiola.route.configureMonitoring
import com.mshdabiola.route.configureRouting
import com.mshdabiola.route.userRoute
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.jetbrains.exposed.sql.Database
import org.koin.core.logger.Logger
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.sql.Connection
import java.sql.DriverManager
import kotlin.math.log
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single {

                val configTemp = HikariConfig().apply {
                    jdbcUrl = "jdbc:sqlite::memory:" // In-memory SQLite
                    driverClassName = "org.sqlite.JDBC" // SQLite driver
                    maximumPoolSize = 1 // Often sufficient for in-memory
                    isAutoCommit = false // or true, depending on your needs
                    transactionIsolation = "TRANSACTION_SERIALIZABLE" // Adjust if needed
                    validate()
                }
                val configSqlite = HikariConfig().apply {
                    jdbcUrl = "jdbc:sqlite:file:./school.db" // SQLite file path
                    driverClassName = "org.sqlite.JDBC" // SQLite driver
                    maximumPoolSize = 3 // Adjust as needed for SQLite
                    isAutoCommit = false // or true, depending on your needs
                    transactionIsolation = "TRANSACTION_SERIALIZABLE" // Adjust if needed
                    validate()
                }

                val configPostgres = HikariConfig().apply {
                    jdbcUrl = environment.config.propertyOrNull("postgres.url")?.getString() ?: ""
                    driverClassName =
                        "org.postgresql.Driver" // Replace with your driver com.mysql.cj.jdbc.Driver
                    username = environment.config.propertyOrNull("postgres.user")?.getString()
                        ?: ""
                    password = environment.config.propertyOrNull("postgres.password")?.getString()
                        ?: ""
                    maximumPoolSize = 10 // Adjust as needed
                    isAutoCommit = false // or true, depending on your needs
                    transactionIsolation = "TRANSACTION_REPEATABLE_READ" // Adjust if needed
                    validate()
                }
                val dataSource = HikariDataSource(configPostgres)
                Database.connect(dataSource)
            }
            single<UserService> {
                val database = get<Database>()
                UserService(database)
            }
        })
    }
    install(ContentNegotiation) {
        json()
    }
//    install(Resources)
    install(AutoHeadResponse)


    configureMonitoring()
    configureRouting()
    userRoute()
}
