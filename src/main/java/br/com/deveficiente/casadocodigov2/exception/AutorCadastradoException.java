package br.com.deveficiente.casadocodigov2.exception;

import org.springframework.http.HttpStatus;

public class AutorCadastradoException extends CasaDoCodigoException {
    public AutorCadastradoException(String description) {
        super(HttpStatus.CONFLICT, "autor_cad_exception", description);
    }
}
