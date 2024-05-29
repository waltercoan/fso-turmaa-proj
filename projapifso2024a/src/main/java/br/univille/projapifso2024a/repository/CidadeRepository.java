package br.univille.projapifso2024a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projapifso2024a.entity.Cidade;



@Repository
public interface CidadeRepository
    extends JpaRepository<Cidade,Long> {
    
}
