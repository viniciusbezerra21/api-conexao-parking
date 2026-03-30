package conexao_parking.api.infra.exceptions;

import conexao_parking.api.domain.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    private record DadosErroValidacao(
            String campo,
            String mensagem
    ) {
        public DadosErroValidacao (FieldError erro) {
            this(
                    erro.getField(),
                    erro.getDefaultMessage()
            );
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarErro409(DataIntegrityViolationException ex) {
        String mensagem = "Erro de integridade de dados.";


        if (ex.getMessage() != null && ex.getMessage().contains("Duplicate entry")) {

            mensagem = "Já existe um registro com estes dados (CPF ou Placa) no sistema.";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DadosErroSimples(mensagem));
    }

    public class VeiculoBloqueadoException extends RuntimeException {
        public VeiculoBloqueadoException() {
            super("Veículo bloqueado");
        }
    }

    private record DadosErroSimples(String mensagem) {}
}

