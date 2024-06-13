package com.example.monchisbooks.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBusqueda(
        @JsonAlias ("count") Integer busquedasEncontradas,
        @JsonAlias ("results")List<DatosLibro> libros
        ) {
}
