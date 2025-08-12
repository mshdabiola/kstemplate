/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.kstemplate

import com.mshdabiola.kstemplate.database.service.UserService
import com.mshdabiola.kstemplate.route.configureMonitoring
import com.mshdabiola.kstemplate.route.configureRouting
import com.mshdabiola.kstemplate.route.userRoute
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.jetbrains.exposed.v1.jdbc.Database
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    EngineMain
        .main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(
            module {
                single {
//                    val configTemp =
//                        HikariConfig().apply {
//                            jdbcUrl = "jdbc:sqlite::memory:" // In-memory SQLite
//                            driverClassName = "org.sqlite.JDBC" // SQLite driver
//                            maximumPoolSize = 1 // Often sufficient for in-memory
//                            isAutoCommit = false // or true, depending on your needs
//                            transactionIsolation = "TRANSACTION_SERIALIZABLE" // Adjust if needed
//                            validate()
//                        }
//                    val configSqlite =
//                        HikariConfig().apply {
//                            jdbcUrl = "jdbc:sqlite:file:./school.db" // SQLite file path
//                            driverClassName = "org.sqlite.JDBC" // SQLite driver
//                            maximumPoolSize = 3 // Adjust as needed for SQLite
//                            isAutoCommit = false // or true, depending on your needs
//                            transactionIsolation = "TRANSACTION_SERIALIZABLE" // Adjust if needed
//                            validate()
//                        }

                    val configPostgres =
                        HikariConfig().apply {
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
            },
        )
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
