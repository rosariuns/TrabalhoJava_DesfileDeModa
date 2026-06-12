package com.unipar.desfile.repositories;

import com.unipar.desfile.models.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
}