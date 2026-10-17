
package com.example.chew.service.impl;

import com.example.chew.dto.*;
import com.example.chew.entity.*;
import com.example.chew.repository.*;
import com.example.chew.service.PedidoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final MesaRepository mesaRepository;
    private final GrupoMesaRepository grupoMesaRepository;
    private final GrupoMesaDetalleRepository grupoMesaDetalleRepository;
    private final PedidoRepository pedidoRepository;
    private final DetalleProductoRepository detalleProductoRepository;
    private final SeguimientoCocinaRepository seguimientoCocinaRepository;
    private final PlatoRepository platoRepository;
    private final BebidaRepository bebidaRepository;

    public PedidoServiceImpl(
            MesaRepository mesaRepository,
            GrupoMesaRepository grupoMesaRepository,
            GrupoMesaDetalleRepository grupoMesaDetalleRepository,
            PedidoRepository pedidoRepository,
            DetalleProductoRepository detalleProductoRepository,
            SeguimientoCocinaRepository seguimientoCocinaRepository,
            PlatoRepository platoRepository,
            BebidaRepository bebidaRepository) {

        this.mesaRepository = mesaRepository;
        this.grupoMesaRepository = grupoMesaRepository;
        this.grupoMesaDetalleRepository = grupoMesaDetalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.detalleProductoRepository = detalleProductoRepository;
        this.seguimientoCocinaRepository = seguimientoCocinaRepository;
        this.platoRepository = platoRepository;
        this.bebidaRepository = bebidaRepository;
    }

    @Override
    @Transactional
    public PedidoResponseDTO crearPedidoMesa(
            PedidoRequestDTO solicitud) {

        // 1. Validar el limite total de productos
        int cantidadTotal = solicitud.items()
                .stream()
                .mapToInt(PedidoItemRequestDTO::cantidad)
                .sum();

        if (cantidadTotal > 7) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El pedido no puede superar 7 productos"
            );
        }

        // 2. Buscar y bloquear la mesa
        Mesa mesa = mesaRepository
                .bloquearPorNumeroMesa(solicitud.numeroMesa())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La mesa no existe"
                ));

        if (!Boolean.TRUE.equals(mesa.getActivo())
                || !Boolean.TRUE.equals(mesa.getQrActivo())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "La mesa o su codigo QR no estan activos"
            );
        }

        // 3. Validar el token QR
        if (mesa.getQrToken() == null
                || !MessageDigest.isEqual(
                        mesa.getQrToken().getBytes(
                                StandardCharsets.UTF_8),
                        solicitud.tokenMesa().getBytes(
                                StandardCharsets.UTF_8))) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Token QR invalido"
            );
        }

        // 4. Obtener o crear el grupo activo
        List<GrupoMesa> grupos =
                grupoMesaRepository.buscarGruposActivosDeMesa(
                        mesa.getIdMesa());

        if (grupos.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La mesa pertenece a varios grupos activos"
            );
        }

        GrupoMesa grupo;

        if (grupos.isEmpty()) {

            grupo = new GrupoMesa();
            grupo.setNombreGrupo(
                    "Mesa " + mesa.getNumeroMesa());
            grupo.setMesaPrincipal(mesa.getNumeroMesa());
            grupo.setEstado("activo");

            grupo = grupoMesaRepository.saveAndFlush(grupo);

            GrupoMesaDetalle relacion =
                    new GrupoMesaDetalle();

            relacion.setIdGrupoMesa(grupo.getIdGrupoMesa());
            relacion.setIdMesa(mesa.getIdMesa());

            grupoMesaDetalleRepository.saveAndFlush(relacion);

        } else {

            grupo = grupos.get(0);
        }

        // 5. Registrar el pedido
        Pedido pedido = new Pedido();

        pedido.setIdGrupoMesa(grupo.getIdGrupoMesa());
        pedido.setIdUsuario(null);
        pedido.setTipoPedido("mesa");
        pedido.setNombreCliente(
                "Mesa " + mesa.getNumeroMesa());
        pedido.setTelefonoLlevar(null);
        pedido.setEstado("pendiente");
        pedido.setRegistradoPor(null);

        pedido = pedidoRepository.saveAndFlush(pedido);

        // 6. Guardar productos y seguimiento
        BigDecimal total = BigDecimal.ZERO;

        for (PedidoItemRequestDTO item : solicitud.items()) {

            BigDecimal precio = obtenerPrecioActual(item);

            BigDecimal subtotal = precio.multiply(
                    BigDecimal.valueOf(item.cantidad())
            );

            DetalleProducto detalle = new DetalleProducto();

            detalle.setIdPedido(pedido.getIdPedido());
            detalle.setTipoProducto(item.tipoProducto());

            if ("plato".equals(item.tipoProducto())) {
                detalle.setIdPlato(item.idProducto());
                detalle.setIdBebida(null);
            } else {
                detalle.setIdBebida(item.idProducto());
                detalle.setIdPlato(null);
            }

            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(subtotal);
            detalle.setObservacion(item.observacion());

            detalle = detalleProductoRepository
                    .saveAndFlush(detalle);

            SeguimientoCocina seguimiento =
                    new SeguimientoCocina();

            seguimiento.setIdDetalleProducto(
                    detalle.getIdDetalleProducto());

            seguimiento.setEstado("recibido");

            seguimientoCocinaRepository.save(seguimiento);

            total = total.add(subtotal);
        }

        // 7. Devolver la confirmacion
        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                mesa.getNumeroMesa(),
                grupo.getIdGrupoMesa(),
                "PED-" + pedido.getIdPedido(),
                pedido.getEstado(),
                total
        );
    }

    private BigDecimal obtenerPrecioActual(
            PedidoItemRequestDTO item) {

        Object precio;

        if ("plato".equals(item.tipoProducto())) {

            Plato plato = platoRepository
                    .findById(item.idProducto())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "El plato solicitado no existe"
                    ));

            if (!Boolean.TRUE.equals(plato.getActivo())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "El plato solicitado esta inactivo"
                );
            }

            precio = plato.getPrecio();

        } else if ("bebida".equals(item.tipoProducto())) {

            Bebida bebida = bebidaRepository
                    .findById(item.idProducto())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "La bebida solicitada no existe"
                    ));

            if (!Boolean.TRUE.equals(bebida.getActivo())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "La bebida solicitada esta inactiva"
                );
            }

            precio = bebida.getPrecio();

        } else {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de producto no valido"
            );
        }

        if (precio == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El producto no tiene un precio valido"
            );
        }

        BigDecimal valor = new BigDecimal(
                precio.toString()
        ).setScale(2, RoundingMode.HALF_UP);

        if (valor.signum() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El precio del producto debe ser positivo"
            );
        }

        return valor;
    }
}
