package br.com.deveficiente.casadocodigov2.repository;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}
