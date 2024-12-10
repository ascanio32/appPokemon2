package com.example.pokemonapp.model;

import java.io.Serializable;

public class Ability implements Serializable {
    private AbilityDetail ability;
    private String name;

    public String getName() {// Método para obtener el nombre de la habilidad
        return name;
    }


    public void setName(String name) {// Método para establecer el nombre de la habilidad
        this.name = name;
    }

    public AbilityDetail getAbility() {// Método para obtener los detalles de la habilidad
        return ability;
    }

    public void setAbility(AbilityDetail ability) { // Método para establecer los detalles de la habilidad
        this.ability = ability;
    }

    public static class AbilityDetail implements Serializable {// Clase interna que representa los detalles de una habilidad
        private String name;// Nombre del detalle de la habilidad
        private String url;// URL del detalle de la habilidad

        public String getName() {// Método para obtener el nombre del detalle de la habilidad
            return name;
        }

        public void setName(String name) {// Método para establecer el nombre del detalle de la habilidad
            this.name = name;
        }

        public String getUrl() {// Método para obtener la URL del detalle de la habilidad
            return url;
        }

        public void setUrl(String url) {// Método para establecer la URL del detalle de la habilidad
            this.url = url;
        }
    }
}

