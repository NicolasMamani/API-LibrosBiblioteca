package com.api.rest.biblioteca.apirestrestbibliotecabidireccional.controladores;

import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.entidades.Biblioteca;
import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.entidades.Libro;
import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.repositorios.BibliotecaRepository;
import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.repositorios.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    public ResponseEntity<Page<Biblioteca>> listarBibliotecas(Pageable pageable){
        return ResponseEntity.ok(bibliotecaRepository.findAll(pageable));
    }

    @GetMapping
    public ResponseEntity<Page<Libro>> listarLibros(Pageable pageable){
        return ResponseEntity.ok(libroRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Libro> guardarLibro(@Valid @RequestBody Libro libro){
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        if(!bibliotecaOptional.isPresent()) {
            //retornamos algo vacio que indica que no se ha podido procesar la entidad
            return ResponseEntity.unprocessableEntity().build();
        }
        libro.setBiblioteca(bibliotecaOptional.get());
        Libro libroGuardado =libroRepository.save(libro);
//        Creamos una nueva URI del libro que se esta creando
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(libroGuardado.getId()).toUri();
        return ResponseEntity.created(ubicacion).body(libroGuardado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@Valid @RequestBody Libro libro, @PathVariable Integer id){
        Optional<Biblioteca> bibliotecaOptional = bibliotecaRepository.findById(libro.getBiblioteca().getId());
        //Si no encontramos la biblioteca devolvemos una "entidad no procesable"
        if(!bibliotecaOptional.isPresent()) {
            //retornamos algo vacio que indica que no se ha podido procesar la entidad
            return ResponseEntity.unprocessableEntity().build();
        }
        //Ahora buscamos le id del libro que nos dieron por URL
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if(!libroOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        //En este punto encontramos el libro y la biblioteca
        libro.setBiblioteca(bibliotecaOptional.get());
        libro.setId(libroOptional.get().getId());
        libroRepository.save(libro);
        return ResponseEntity.noContent().build();
//        libro.setBiblioteca(bibliotecaOptional.get());
//        Libro libroGuardado =libroRepository.save(libro);
////        Creamos una nueva URI del libro que se esta creando
//        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(libroGuardado.getId()).toUri();
    }

    @DeleteMapping
    public ResponseEntity<Libro> eliminarLibro(@PathVariable Integer id){
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if(!libroOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        libroRepository.delete(libroOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarLibroPorId(@PathVariable Integer id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if(!libroOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(libroOptional.get());
    }
}
