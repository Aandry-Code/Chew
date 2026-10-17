
package com.example.chew.exception;

public class CodigoBebidaDuplicadoException
        extends RuntimeException {

    public CodigoBebidaDuplicadoException(
            String codigoBebida) {

        super("Ya existe una bebida registrada con el codigo: "
                + codigoBebida);
    }
}
