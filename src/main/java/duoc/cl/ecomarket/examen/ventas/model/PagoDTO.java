package duoc.cl.ecomarket.examen.ventas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Integer pagoId;
    private LocalDateTime fechaPago;
    private String estado;
    private Integer monto;
    private String metodoPago;
    private Integer idProducto;
    private Integer idUsuario;

}
