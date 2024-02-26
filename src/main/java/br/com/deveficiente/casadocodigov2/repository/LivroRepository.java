package br.com.deveficiente.casadocodigov2.repository;

import br.com.deveficiente.casadocodigov2.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
