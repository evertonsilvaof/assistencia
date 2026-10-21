package com.assistencia.repository;
import com.assistencia.entity.OrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long> {
    boolean existsByNumeroProtocolo(String numeroProtocolo);
}
