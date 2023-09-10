package com.api.rest.biblioteca.apirestrestbibliotecabidireccional.repositorios;
import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Integer> {
}
