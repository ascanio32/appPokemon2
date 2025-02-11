package com.example.pokemonapp.model;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {
    private int number;// Número del Pokémon
    private String name;// Nombre del Pokémon
    private String url; // URL de la información del Pokémon
    private int base_experience;// Experiencia base del Pokémon
    private int height;// Altura del Pokémon
    private int weight; // Peso del Pokémon
    private List<Ability> abilities;// Lista de habilidades del Pokémon
    private int id;

    public int getIdentificacion() {
        return id;
    }

    public void setIdentificacion(int identificacion) {
        this.id = identificacion;
    }
// Getters y setters Para obtener y establecer

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        if (url != null) {
            String[] urlPartes = url.split("/");
            return Integer.parseInt(urlPartes[urlPartes.length - 1]);
        } else {
            return number;
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }
}
