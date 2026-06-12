package com.unipar.desfile.repositories;

import com.unipar.desfile.models.Desfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesfileRepository extends JpaRepository<Desfile, Long> {
}