package com.assistencia.repository;
import com.assistencia.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {}
