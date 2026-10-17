
package com.example.chew.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record PedidoRequestDTO(

    @NotNull
    @Positive
    Integer numeroMesa,

    @NotBlank
    String tokenMesa,

    @NotEmpty
    @Size(max = 7)
    List<@NotNull @Valid PedidoItemRequestDTO> items

) {}
