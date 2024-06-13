package com.example.monchisbooks.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias ("name") String nombre,
        @JsonAlias ("birth_year") Integer añoDeNacimiento,
        @JsonAlias ("death_year") Integer añoDeFallecimiento
) {
}
