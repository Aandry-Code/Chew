
package com.example.chew.exception;

public class CodigoPlatoDuplicadoException extends RuntimeException {

    public CodigoPlatoDuplicadoException(String codigoPlato) {

        super("Ya existe un plato registrado con el codigo: " + codigoPlato);
    }
}
