package duoc.cl.ecomarket.examen.ventas.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDTO {
    private Integer idUsuario;
    private Integer idProducto;
    private String metodoPago;
}
