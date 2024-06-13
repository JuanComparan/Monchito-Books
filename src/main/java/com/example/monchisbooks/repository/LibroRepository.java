package com.example.monchisbooks.repository;

import com.example.monchisbooks.model.Idioma;
import com.example.monchisbooks.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE :año < l.autor.añoDeFallecimiento ORDER BY l.autor.añoDeNacimiento")
    List<Libro> libroPorAutorVivo(int año);

    List<Libro> findByIdioma(Idioma idioma);
}
