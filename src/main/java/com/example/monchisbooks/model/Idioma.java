package com.example.monchisbooks.model;
 
public enum Idioma {
        ESPANOL("es", "Español"),
        INGLES("en", "Inglés"),
        FRANCES("fr", "Fránces");

        private String idioma;
        private String idiomaEspanol;

        Idioma (String idioma, String idiomaEspanol){
            this.idioma = idioma;
            this.idiomaEspanol = idiomaEspanol;
        }

        public static Idioma fromString(String text){
            for (Idioma idioma : Idioma.values()){
                if (idioma.idioma.equalsIgnoreCase(text)){
                    return idioma;
                }
            }
            throw new IllegalArgumentException("Ninguna idioma encontrado: " + text);
        }

        public static Idioma fromSEspanol(String text){
            for (Idioma idioma : Idioma.values()){
                if (idioma.idiomaEspanol.equalsIgnoreCase(text)){
                    return idioma;
                }
            }
            throw new IllegalArgumentException("Ninguna idioma encontrado: " + text);
        }
}
