
package com.example.chew.dto;

import jakarta.validation.constraints.*;

public record PedidoItemRequestDTO(

    @NotBlank
    @Pattern(regexp = "plato|bebida")
    String tipoProducto,

    @NotNull
    @Positive
    Integer idProducto,

    @NotNull
    @Min(1)
    @Max(7)
    Integer cantidad,

    @Size(max = 500)
    String observacion

) {}
