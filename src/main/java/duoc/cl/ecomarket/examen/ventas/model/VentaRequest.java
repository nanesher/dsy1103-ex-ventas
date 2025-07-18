package duoc.cl.ecomarket.examen.ventas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VentaRequest {

    private Integer idUsuario;
    private Integer idProducto;
    private String metodoPago;
    private String tipoEnvio;
}
