package br.univille.projfso2024a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.projfso2024a.entity.Cliente;

@Repository
public interface ClienteRepository 
        extends JpaRepository<Cliente,Long>{
    
}
