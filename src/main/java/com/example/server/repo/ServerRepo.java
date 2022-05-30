package com.example.server.repo;

import com.example.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ServerRepo extends JpaRepository<Server, Long> {

    Server findByIpAddress(String ipAddress);

}
