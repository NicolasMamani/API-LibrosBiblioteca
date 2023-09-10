package com.api.rest.biblioteca.apirestrestbibliotecabidireccional.controladores;

import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.entidades.Biblioteca;
import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.repositorios.BibliotecaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {
    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    public ResponseEntity<Page<Biblioteca>> listarBibliotecas(Pageable pageable){
        return ResponseEntity.ok(bibliotecaRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Biblioteca> guardarBiblioteca(@Valid @RequestBody Biblioteca biblioteca){
        Biblioteca bibliotecaGuardada = bibliotecaRepository.save(biblioteca);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bibliotecaGuardada.getId()).toUri();
        return ResponseEntity.created(ubicacion).body(bibliotecaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> editarBiblioteca(@PathVariable Integer id,@Valid @RequestBody Biblioteca biblioteca){
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(id);
        //Si la biblioteca no existe
        if(!bibliotecaOptional.isPresent()) {
            //retornamos algo vacio que indica que no se ha podido procesar la entidad
            return ResponseEntity.unprocessableEntity().build();
        }
        biblioteca.setId(bibliotecaOptional.get().getId());
        bibliotecaRepository.save(biblioteca);
        //indicamos que no estamos enviando ningun contenido y estamos contruyendo
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Biblioteca> eliminarBiblioteca(@PathVariable Integer id) {
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(id);
        if(!bibliotecaOptional.isPresent()) {
            //retornamos algo vacio que indica que no se ha podido procesar la entidad
            return ResponseEntity.unprocessableEntity().build();
        }
        bibliotecaRepository.delete(bibliotecaOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> obtenerBibliotecaPorId(@PathVariable Integer id){
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(id);
        if(!bibliotecaOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(bibliotecaOptional.get());
    }
}
