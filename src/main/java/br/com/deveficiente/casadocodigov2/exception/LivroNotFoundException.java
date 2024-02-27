package br.com.deveficiente.casadocodigov2.exception;

import org.springframework.http.HttpStatus;

public class LivroNotFoundException extends CasaDoCodigoException {
    public LivroNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "livro_not_found", "Nenhum livro encontrado com o id: " + id);
    }
}
