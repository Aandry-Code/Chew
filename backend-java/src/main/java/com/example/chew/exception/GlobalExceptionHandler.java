
package com.example.chew.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar errores de validacion HTTP 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("status", 400);
        respuesta.put("mensaje", "Los datos enviados no son validos");
        respuesta.put("errores", errores);

        return ResponseEntity.badRequest().body(respuesta);
    }

    // Manejar codigos de platos duplicados HTTP 409
    @ExceptionHandler(CodigoPlatoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> manejarCodigoDuplicado(
            CodigoPlatoDuplicadoException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("status", HttpStatus.CONFLICT.value());
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuesta);
    }
    
    // Manejar codigos de bebidas duplicados
    @ExceptionHandler(CodigoBebidaDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> manejarCodigoBebidaDuplicado(
            CodigoBebidaDuplicadoException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("status", HttpStatus.CONFLICT.value());
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuesta);
    }

}
