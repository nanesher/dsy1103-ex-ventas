package duoc.cl.ecomarket.examen.ventas.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ProductoDTO {
    private Integer productoId;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String material;
    private String vidaUtil;
    private int stock;
    private int precio;


}
