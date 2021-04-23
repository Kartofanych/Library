package com.example.samatdanilonelove.models;

import java.util.Date;

public class History {
    String name, autor, about, img, genre;
    Long pop;


    public History(String Name, String Autor, String About, Long Pop, String Img, String Genre){

        this.autor = Autor;
        this.name = Name;
        this.about = About;
        this.pop = Pop;
        this.img = Img;
        this.genre = Genre;

    }
    public History(){

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Long getPop() {
        return pop;
    }

    public void setPop(Long pop) {
        this.pop = pop;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
