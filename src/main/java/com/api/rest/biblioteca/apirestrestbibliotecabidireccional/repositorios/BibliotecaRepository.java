package com.api.rest.biblioteca.apirestrestbibliotecabidireccional.repositorios;

import com.api.rest.biblioteca.apirestrestbibliotecabidireccional.entidades.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecaRepository extends JpaRepository<Biblioteca,Integer> {

}
