package br.com.deveficiente.casadocodigov2.exception;

import org.springframework.http.HttpStatus;

public class AutorNotFoundException extends CasaDoCodigoException {
    public AutorNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "autor_not_found", "Nenhum autor encontrado com o id: " + id);
    }
}
