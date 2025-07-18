package duoc.cl.ecomarket.examen.ventas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="ventas")
public class Venta {
    @Schema(description = "Identificador unico de la venta", example = "1")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="venta_id")
    private Integer ventaId;

    @Schema(description = "Fecha en la que fue realizada la venta de la venta", example = "2025-01-01")
    @Column(name="fecha_venta",nullable=false)
    private LocalDate fechaVenta;

    @Schema(description = "Identificador unico del producto de la venta", example = "3")
    @Column(name="producto_id",nullable = false)
    private Integer productoId;

    @Schema(description = "Precio en pesos chilenos de la venta", example = "4990")
    @Column(name = "precio_venta", nullable = false)
    private Integer precioVenta;

    @Schema(description = "Identificador unico del usuario de la venta", example = "5")
    @Column(name="usuario_id",nullable = false)
    private Integer usuarioId;

    @Schema(description = "Identificador unico del envio de la venta", example = "14")
    @Column(name="envio_id",nullable = false)
    private Integer envioId;

    @Schema(description = "Identificador unico del pago de la venta", example = "9")
    @Column(name="pago_id",nullable = false)
    private Integer pagoId;

    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDate.now();
    }











}
