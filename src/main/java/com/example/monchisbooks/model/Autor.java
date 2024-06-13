package com.example.monchisbooks.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private Integer añoDeNacimiento;
    private Integer añoDeFallecimiento;
    @OneToOne(mappedBy = "autor")
    private Libro libro;


    public Autor(DatosAutor a){
        this.nombre = a.nombre();
        try {
            this.añoDeNacimiento = a.añoDeNacimiento();
        } catch (NumberFormatException e) {
            this.añoDeNacimiento = null;
        }
        try {
            this.añoDeFallecimiento = a.añoDeFallecimiento();
        } catch (NumberFormatException e) {
            this.añoDeFallecimiento = null;
        }
    }

    public Autor() {

    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(Integer añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    public Integer getAñoDeFallecimiento() {
        return añoDeFallecimiento;
    }

    public void setAñoDeFallecimiento(Integer añoDeFallecimiento) {
        this.añoDeFallecimiento = añoDeFallecimiento;
    }

    @Override
    public String toString() {
        return """
                    \n\t\tNombre: %s
                    \tAño de Nacimiento: %d
                    \tAño de Fallecimiento: %d
                """.formatted(nombre,añoDeNacimiento,añoDeFallecimiento);
    }

}
