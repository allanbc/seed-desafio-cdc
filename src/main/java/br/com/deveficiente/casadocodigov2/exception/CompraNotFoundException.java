package br.com.deveficiente.casadocodigov2.exception;

import org.springframework.http.HttpStatus;

public class CompraNotFoundException extends CasaDoCodigoException {
    public CompraNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "compra_not_found", "Nenhuma compra encontrada com o id: " + id);
    }
}
