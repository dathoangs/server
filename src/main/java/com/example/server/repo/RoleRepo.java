package com.example.server.repo;

import com.example.server.enumeration.ERole;
import com.example.server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
