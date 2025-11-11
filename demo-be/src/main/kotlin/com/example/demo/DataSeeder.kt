package com.example.demo

import io.github.serpro69.kfaker.Faker
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataSeeder {

    @Bean
    fun seedUsers(userRepository: UserRepository) = ApplicationRunner {
        if (userRepository.count() == 0L) {
            val faker = Faker()
            val users = (1..100000).map {
                User(
                        name = faker.name.name(),
                        email = faker.internet.email()
                )
            }

            // Lưu theo batch nhỏ nếu cần
            users.chunked(100).forEach { batch ->
                userRepository.saveAll(batch)
            }

            println("Seeded ${users.size} users into database.")
        } else {
            println("Users already exist, skipping seeding.")
        }
    }
}
