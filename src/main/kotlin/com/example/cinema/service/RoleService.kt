package com.example.cinema.service

import com.example.cinema.model.Role
import com.example.cinema.model.RoleName
import com.example.cinema.repository.RoleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface RoleService {
    fun save(role: Role): Mono<Role>

    fun findByName(name: RoleName): Mono<Role>
}

@Service
class RoleServiceImpl(private val roleRepository: RoleRepository): RoleService {
    override fun save(role: Role) = roleRepository.save(role)

    override fun findByName(name: RoleName) = roleRepository.findByRoleName(name)
}