package duoc.cl.ecomarket.examen.ventas.controller;

import duoc.cl.ecomarket.examen.ventas.assemblers.VentaModelAssembler;
import duoc.cl.ecomarket.examen.ventas.model.Venta;
import duoc.cl.ecomarket.examen.ventas.model.VentaRequest;
import duoc.cl.ecomarket.examen.ventas.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ventas")
@Tag(name="Ventas",description = "Operaciones relacionadas con el registro de las ventas")
public class VentaController  {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private VentaModelAssembler assembler;

    @Operation(summary="Obtener todas las ventas",description = "Obtiene una lista de todas las ventas con sus atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas las ventas listadas correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Venta>> getAll() {
        List<EntityModel<Venta>> ventas = ventaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaController.class).getAll()).withSelfRel(),
                linkTo(methodOn(VentaController.class).create(null)).withRel("Crear una venta(con metodo post)"));
    }

    @Operation(summary="Crear una venta",description = "Se crea una nueva venta con los parametros enviados sin incluir el id asignadole una ID automaticamente" +
            " y el precio se actualizará automaticamente con el precio del producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta creada con éxito",
                    content = @Content(mediaType = "application/hal+json"))
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody VentaRequest ventaInicial) {
            Venta venta = ventaService.save(ventaInicial);
            return ResponseEntity.ok(assembler.toModel(venta));
    }
    @Operation(summary="Obtener una venta",description = "Obtiene una venta con sus atributos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta listado correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })

    @GetMapping (value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Venta> getById(@Parameter(
            description = "ID de la venta a obtener",
            name = "id",
            required = true,
            in = ParameterIn.PATH,
            example = "5"
    )@PathVariable Integer id) {
        Venta venta = ventaService.findById(id);
        return assembler.toModel(venta);
    }

    @Operation(summary="Eliminar una venta",description = "Se elimina una venta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> delete(@Parameter(
            description = "ID de la venta a eliminar",
            name = "id",
            required = true,
            in = ParameterIn.PATH,
            example = "5"
    )@PathVariable Integer id) {
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary="Actualizar una venta",description = "Se actualizan los datos de una venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente",
                    content = @Content(mediaType = "application/hal+json"))
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Venta> updateVenta(@Parameter(
            description = "ID de la venta a actualizar",
            name = "id",
            required = true,
            in = ParameterIn.PATH,
            example = "5"
    )@PathVariable Integer id, @RequestBody Venta venta){
        Venta ventaActualizada = ventaService.update(id, venta);
        return assembler.toModel(ventaActualizada);
    }

}

