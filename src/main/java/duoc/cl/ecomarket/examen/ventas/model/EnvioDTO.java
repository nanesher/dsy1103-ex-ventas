package duoc.cl.ecomarket.examen.ventas.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvioDTO {
    private Integer envioId;
    private LocalDate fechaEntrega;
    private LocalDateTime fechaSolicitud;
    private String direccionEntrega;
    private String tipoEntrega;
    private String estado;
    private String nombreCliente;
    private Integer idUsuario;
}
