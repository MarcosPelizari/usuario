package com.java.usuario.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.usuario.infrastructure.entity.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

}
