package com.example.monchisbooks.model;

import jakarta.persistence.*;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer descargas;

    public Libro(DatosLibro d){
        this.titulo = d.titulo();
        for (DatosAutor a : d.autor()) {
            this.autor = new Autor(a);
        }
        for (String a : d.lenguaje()) {
            this.idioma = Idioma.fromString(a.split(",") [0].trim());
        }
        this.descargas = d.descargas();
    }

    public Libro() {}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return """
                ---------- LIBRO ----------
                \tTitulo: %s
                \tAutor: %s
                \tIdioma: %s
                \tDescargas: %s
                ----------       ----------
                """.formatted(titulo, autor.getNombre(), idioma, descargas);
    }
}
