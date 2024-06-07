package br.com.deveficiente.casadocodigov2.repository;

import br.com.deveficiente.casadocodigov2.entity.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {
    Cupom getByCodigo(String s);
}
