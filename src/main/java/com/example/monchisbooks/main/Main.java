package com.example.monchisbooks.main;

import com.example.monchisbooks.model.Autor;
import com.example.monchisbooks.model.DatosBusqueda;
import com.example.monchisbooks.model.Idioma;
import com.example.monchisbooks.model.Libro;
import com.example.monchisbooks.repository.LibroRepository;
import com.example.monchisbooks.service.ConsumoAPI;
import com.example.monchisbooks.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private String json = consumoAPI.obtenerDatos(URL_BASE);
    private DatosBusqueda datosLibros = conversor.obtenerDatos(json, DatosBusqueda.class);
    private LibroRepository repositorio;
    private Optional<Libro> libroBuscado;
    private List<Libro> libros;

    public Main(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ----- BIENVENIDO -----
                    
                    1.- Buscar libro
                    2.- Mostrar lista de libros buscados
                    3.- Mostrar lista de autores
                    4.- Mostrar autores vivos en determinado año
                    5.- Mostrar libros por idioma
                                        
                    0.- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    mostrarAutoresVivosEnDeterminadoAño();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("\nIngresa el nombre del libro que quieres buscar:");
        var nombreLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosBusqueda.class);

        libroBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .map(l -> new Libro(l))
                .findFirst();

        // Mostrar el libro pedido
        if(libroBuscado.isPresent()){
            repositorio.save(libroBuscado.get());
            System.out.println("\n\t\tLibro encontrado!!");
            System.out.println(
                    """
                
                ---------- LIBRO ----------
                \tTitulo: %s
                \tAutor: %s
                \tIdioma: %s
                \tDescargas: %s
                ----------       ----------
                """.formatted(libroBuscado.get().getTitulo(), libroBuscado.get().getAutor().getNombre(), libroBuscado.get().getIdioma(), libroBuscado.get().getDescargas())
            );
        } else {
            System.out.println("Libro no encontrado!!");
        }
    }

    private void mostrarLibrosBuscados() {
        libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }

    private void mostrarAutores(){
        libros = repositorio.findAll();
        libros.stream()
                .forEach(l -> System.out.println(
                        """
                                Autor: %s
                                Fecha de nacimiento: %s
                                Fecha de fallecimiento: %s
                                Libro: %s
                                """.formatted(l.getAutor().getNombre(), l.getAutor().getAñoDeNacimiento(), l.getAutor().getAñoDeFallecimiento(), l.getTitulo())
                ));
    }


    private void mostrarLibrosPorIdioma(){
        System.out.println("""
                Escriba el idioma por el cual quiere filtrar: 
                    es - Español
                    en - Inglés
                    fr - Fránces
                """);
        var lenguaje = teclado.nextLine();
        var idioma = Idioma.fromString(lenguaje);
        List<Libro> librosPorIdioma = repositorio.findByIdioma(idioma);
        System.out.println("Los libros por idioma " + idioma + " son:");
        librosPorIdioma.forEach(System.out::println);
    }

    private void mostrarAutoresVivosEnDeterminadoAño(){
        System.out.println("¿De que año quiere ver los autores vivos?: ");
        var año = teclado.nextInt();
        List<Libro> librosPorAutorVivoEnAño = repositorio.libroPorAutorVivo(año);
        System.out.println("\t----- Libros Filtrados -----");
        librosPorAutorVivoEnAño.stream()
                .limit(2)
                .forEach(l -> System.out.println(
                        """
                               Autor: %s
                                Fecha de nacimiento: %s
                                Fecha de fallecimiento: %s
                                Libro: %s
                                """.formatted(l.getAutor().getNombre(), l.getAutor().getAñoDeNacimiento(), l.getAutor().getAñoDeFallecimiento(), l.getTitulo())
                ));
    }
}