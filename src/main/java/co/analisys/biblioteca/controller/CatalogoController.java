package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Libro;
import co.analisys.biblioteca.model.LibroId;
import co.analisys.biblioteca.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class CatalogoController {
    private final CatalogoService catalogoService;

    @Autowired
    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(
            summary = "Obtener información de un libro",
            description = "Este endpoint permite obtener la información de un libro específico proporcionando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado y devuelto exitosamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public Libro obtenerLibro(@PathVariable String id) {
        return catalogoService.obtenerLibro(new LibroId(id));
    }

    @Operation(
            summary = "Verificar si un libro está disponible",
            description = "Este endpoint permite verificar si un libro específico está disponible proporcionando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad del libro consultada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}/disponible")
    public boolean isLibroDisponible(@PathVariable String id) {
        Libro libro = catalogoService.obtenerLibro(new LibroId(id));
        return libro != null && libro.isDisponible();
    }

    @Operation(
            summary = "Actualizar la disponibilidad de un libro",
            description = "Este endpoint permite actualizar la disponibilidad de un libro específico proporcionando su ID y el estado de disponibilidad en el cuerpo de la solicitud."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad del libro actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PutMapping("/{id}/disponibilidad")
    public void actualizarDisponibilidad(@PathVariable String id, @RequestBody boolean disponible) {
        catalogoService.actualizarDisponibilidad(new LibroId(id), disponible);
    }

    @Operation(
            summary = "Buscar libros por criterio",
            description = "Este endpoint permite buscar libros en el catálogo proporcionando un criterio de búsqueda, como el título del libro."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros encontrados y devueltos exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron libros que coincidan con el criterio de búsqueda")
    })
    @GetMapping("/buscar")
    public List<Libro> buscarLibros(@RequestParam String criterio) {
        return catalogoService.buscarLibros(criterio);
    }
}
