package br.com.deveficiente.casadocodigov2.model;
import lombok.Builder;
import lombok.Data;
import java.util.Map;
@Builder
public record ErrorResponse( Integer httpStatusCode, String errorCode, String message, Map<String, String> fields) { 

}
