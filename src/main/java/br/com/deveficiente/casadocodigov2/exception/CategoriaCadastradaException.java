package br.com.deveficiente.casadocodigov2.exception;

import org.springframework.http.HttpStatus;

public class CategoriaCadastradaException extends CasaDoCodigoException {
    public CategoriaCadastradaException(String description) {
        super(HttpStatus.CONFLICT, "categoria_cad_exception", description);
    }
}
