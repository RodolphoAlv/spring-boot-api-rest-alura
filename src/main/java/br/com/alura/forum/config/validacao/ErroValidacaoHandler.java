package br.com.alura.forum.config.validacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroValidacaoHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException exception) {
        List<ErroForm> erros = new ArrayList<>();

        exception
                .getBindingResult()
                .getFieldErrors()
                    .forEach(e -> erros.add(ErroForm.build()
                        .campo(e.getField())
                        .erro(e.getDefaultMessage())
                    ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
