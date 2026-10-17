package com.example.chew;

import com.example.chew.entity.Plato;
import com.example.chew.repository.PlatoRepository;
import com.example.chew.repository.BebidaRepository;

import com.example.chew.entity.GrupoMesa;
import com.example.chew.entity.Pedido;
import com.example.chew.entity.DetalleProducto;
import com.example.chew.entity.SeguimientoCocina;

import com.example.chew.repository.GrupoMesaRepository;
import com.example.chew.repository.PedidoRepository;
import com.example.chew.repository.DetalleProductoRepository;
import com.example.chew.repository.SeguimientoCocinaRepository;

import java.math.BigDecimal;
import java.util.List;

import com.example.chew.entity.Mesa;
import com.example.chew.repository.MesaRepository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoConsultaTddTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PlatoRepository platoRepository;

    @MockitoBean
    private BebidaRepository bebidaRepository;

    @MockitoBean
    private MesaRepository mesaRepository;

    @MockitoBean
    private GrupoMesaRepository grupoMesaRepository;

    @MockitoBean
    private PedidoRepository pedidoRepository;

    @MockitoBean
    private DetalleProductoRepository detalleProductoRepository;

    @MockitoBean
    private SeguimientoCocinaRepository seguimientoCocinaRepository;

    @BeforeEach
    void prepararMesaDePrueba() {

        Mesa mesa = new Mesa();

        mesa.setIdMesa(5);
        mesa.setNumeroMesa(5);
        mesa.setActivo(true);
        mesa.setQrActivo(true);
        mesa.setQrToken("TOKEN_VALIDO_PRUEBA");

        when(mesaRepository.findByNumeroMesa(5))
                .thenReturn(Optional.of(mesa));
    }

    @Test
    void rechazarConsultaConTokenQrInvalido()
            throws Exception {

        mockMvc.perform(
                get("/api/pedidos/mesa/5")
                        .header(
                                "X-Mesa-Token",
                                "TOKEN_INCORRECTO_PRUEBA"
                        )
        )
                .andExpect(status().isForbidden());
    }

    @Test
    void permitirConsultaConTokenQrValido()
            throws Exception {

        mockMvc.perform(
                get("/api/pedidos/mesa/5")
                        .header(
                                "X-Mesa-Token",
                                "TOKEN_VALIDO_PRUEBA"
                        )
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void consultarPedidoConProductosYEstadoCocina()
            throws Exception {

        // Simular un grupo activo de la mesa 5
        GrupoMesa grupo = new GrupoMesa();
        grupo.setIdGrupoMesa(20);
        grupo.setEstado("activo");

        when(grupoMesaRepository.buscarGruposActivosDeMesa(5))
                .thenReturn(List.of(grupo));

        // Simular el pedido PED-2
        Pedido pedido = new Pedido();
        pedido.setIdPedido(2);
        pedido.setIdGrupoMesa(20);
        pedido.setTipoPedido("mesa");
        pedido.setEstado("pendiente");

        when(pedidoRepository
                .findByIdGrupoMesaOrderByFechaCreacionDesc(20))
                .thenReturn(List.of(pedido));

        // Simular un producto del pedido
        DetalleProducto detalle = new DetalleProducto();
        detalle.setIdDetalleProducto(100);
        detalle.setIdPedido(2);
        detalle.setTipoProducto("plato");
        detalle.setIdPlato(1);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("45.50"));
        detalle.setSubtotal(new BigDecimal("91.00"));
        detalle.setObservacion("Sin cebolla");

        when(detalleProductoRepository
                .findByIdPedidoOrderByIdDetalleProductoAsc(2))
                .thenReturn(List.of(detalle));

        // Simular el estado registrado en cocina
        SeguimientoCocina seguimiento = new SeguimientoCocina();
        seguimiento.setIdDetalleProducto(100);
        seguimiento.setEstado("recibido");

        when(seguimientoCocinaRepository
                .findByIdDetalleProducto(100))
                .thenReturn(java.util.Optional.of(seguimiento));

        when(seguimientoCocinaRepository
                .findByIdDetalleProducto(100))
                .thenReturn(java.util.Optional.of(seguimiento));

// Simular el nombre del plato
        Plato plato = new Plato();

        plato.setIdPlato(1);
        plato.setNombre("Plato de prueba");

        when(platoRepository.findById(1))
                .thenReturn(Optional.of(plato));

        
                // Realizar la consulta con un token valido
                mockMvc.perform(
                        get("/api/pedidos/mesa/5")
                                .header(
                                        "X-Mesa-Token",
                                        "TOKEN_VALIDO_PRUEBA"
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.data[0].id_pedido").value(2))
                .andExpect(jsonPath("$.data[0].codigo").value("PED-2"))
                .andExpect(jsonPath("$.data[0].productos[0].cantidad").value(2))
                .andExpect(jsonPath("$.data[0].productos[0].observacion").value("Sin cebolla"))
                .andExpect(jsonPath("$.data[0].productos[0].estadoCocina").value("recibido"))
                .andExpect(jsonPath("$.data[0].productos[0].nombre").value("Plato de prueba"));

    }

}
