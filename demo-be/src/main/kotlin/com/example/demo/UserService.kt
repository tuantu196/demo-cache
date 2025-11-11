package com.example.demo

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    @Cacheable("users")
    fun getAllUsers(): List<User> {
        println("Fetching users from DB (not cache)...")
        return userRepository.findAll()
    }

    @Cacheable(value = ["user"], key = "#id")
    fun getUserById(id: Long): User? {
        println("Fetching user id=$id from DB (not cache)...")
        return userRepository.findById(id).orElse(null)
    }

    @CacheEvict(value = ["users"], allEntries = true)
    @CachePut(value = ["user"], key = "#result.id")
    fun createUser(user: User): User {
        val saved = userRepository.save(user)
        println("Created new user: $saved")
        return saved
    }

    @CacheEvict(value = ["users"], allEntries = true)
    @CachePut(value = ["user"], key = "#id")
    fun updateUser(id: Long, user: User): User? {
        val existing = userRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(name = user.name, email = user.email)
        return userRepository.save(updated)
    }

    @CacheEvict(value = ["users"], allEntries = true)
    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
