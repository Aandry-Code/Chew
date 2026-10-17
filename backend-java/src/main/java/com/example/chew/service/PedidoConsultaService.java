
package com.example.chew.service;
import com.example.chew.service.PedidoConsultaService;

import com.example.chew.dto.PedidoActualDTO;
import com.example.chew.entity.*;
import com.example.chew.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoConsultaService {

    private final GrupoMesaRepository grupoMesaRepository;
    private final PedidoRepository pedidoRepository;
    private final DetalleProductoRepository detalleProductoRepository;
    private final SeguimientoCocinaRepository seguimientoCocinaRepository;
    private final PlatoRepository platoRepository;
    private final BebidaRepository bebidaRepository;

    public PedidoConsultaService(
            GrupoMesaRepository grupoMesaRepository,
            PedidoRepository pedidoRepository,
            DetalleProductoRepository detalleProductoRepository,
            SeguimientoCocinaRepository seguimientoCocinaRepository,
            PlatoRepository platoRepository,
            BebidaRepository bebidaRepository) {

        this.grupoMesaRepository = grupoMesaRepository;
        this.pedidoRepository = pedidoRepository;
        this.detalleProductoRepository = detalleProductoRepository;
        this.seguimientoCocinaRepository = seguimientoCocinaRepository;
        this.platoRepository = platoRepository;
        this.bebidaRepository = bebidaRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoActualDTO> obtenerPedidosDeMesa(
            Integer idMesa) {

        // 1. Buscar el grupo activo de la mesa
        List<GrupoMesa> grupos =
                grupoMesaRepository.buscarGruposActivosDeMesa(
                        idMesa);

        if (grupos == null || grupos.isEmpty()) {
            return List.of();
        }

        if (grupos.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La mesa tiene varios grupos activos"
            );
        }

        Integer idGrupo = grupos.get(0).getIdGrupoMesa();

        // 2. Consultar los pedidos del grupo
        List<Pedido> pedidos =
                pedidoRepository
                        .findByIdGrupoMesaOrderByFechaCreacionDesc(
                                idGrupo);

        List<PedidoActualDTO> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            // Excluir pedidos cancelados o pagados
            if ("cancelado".equalsIgnoreCase(pedido.getEstado())
                    || "pagado".equalsIgnoreCase(pedido.getEstado())) {
                continue;
            }

            // 3. Obtener productos del pedido
            List<DetalleProducto> detalles =
                    detalleProductoRepository
                            .findByIdPedidoOrderByIdDetalleProductoAsc(
                                    pedido.getIdPedido());

            List<PedidoActualDTO.Producto> productos =
                    new ArrayList<>();

            BigDecimal total = BigDecimal.ZERO;

            for (DetalleProducto detalle : detalles) {

                String nombre = obtenerNombreProducto(detalle);

                // 4. Consultar el estado en cocina
                String estadoCocina =
                        seguimientoCocinaRepository
                                .findByIdDetalleProducto(
                                        detalle.getIdDetalleProducto())
                                .map(SeguimientoCocina::getEstado)
                                .orElse("sin seguimiento");

                BigDecimal subtotal =
                        detalle.getSubtotal() != null
                                ? detalle.getSubtotal()
                                : BigDecimal.ZERO;

                productos.add(
                        new PedidoActualDTO.Producto(
                                nombre,
                                detalle.getCantidad(),
                                detalle.getPrecioUnitario(),
                                subtotal,
                                detalle.getObservacion(),
                                estadoCocina
                        )
                );

                total = total.add(subtotal);
            }

            // 5. Formatear fecha y hora
            LocalDateTime fechaCreacion =
                    pedido.getFechaCreacion();

            String fecha = fechaCreacion == null
                    ? ""
                    : fechaCreacion.format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String hora = fechaCreacion == null
                    ? ""
                    : fechaCreacion.format(
                            DateTimeFormatter.ofPattern("HH:mm"));

            // 6. Preparar respuesta
            resultado.add(
                    new PedidoActualDTO(
                            pedido.getIdPedido(),
                            "PED-" + pedido.getIdPedido(),
                            pedido.getEstado(),
                            fecha,
                            hora,
                            total,
                            productos
                    )
            );
        }

        return resultado;
    }

    private String obtenerNombreProducto(
            DetalleProducto detalle) {

        if ("plato".equalsIgnoreCase(
                detalle.getTipoProducto())) {

            return platoRepository
                    .findById(detalle.getIdPlato())
                    .map(Plato::getNombre)
                    .orElse("Plato no disponible");
        }

        if ("bebida".equalsIgnoreCase(
                detalle.getTipoProducto())) {

            return bebidaRepository
                    .findById(detalle.getIdBebida())
                    .map(Bebida::getNombre)
                    .orElse("Bebida no disponible");
        }

        return "Producto desconocido";
    }
}
