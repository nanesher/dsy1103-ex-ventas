package duoc.cl.ecomarket.examen.ventas.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer idUsuario;
    private String nombre;
    private String rut;
    private String email;
    private String direccion;
    private Boolean isValid;

}
