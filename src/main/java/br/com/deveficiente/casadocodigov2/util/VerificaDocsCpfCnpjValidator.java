//package br.com.deveficiente.casadocodigov2.util;
//
//import br.com.deveficiente.casadocodigov2.model.compra.NovaCompraRequest;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//public class VerificaDocsCpfCnpjValidator implements Validator {
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return NovaCompraRequest.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        if(errors.hasErrors()) {
//            return;
//        }
//        NovaCompraRequest request = (NovaCompraRequest) target;
//        if(request.documentoValido()){
//            errors.rejectValue("documento", null, "documento precisa ser cpf ou cnpj");
//        }
//    }
//}
