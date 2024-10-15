package org.aston.application.dto;

import org.aston.application.entity.Author;
import org.aston.application.entity.Language;
import org.aston.application.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class BookTo {

    Long id;

    Author author;

    String title;

    String genre;

    int year;

    Collection<Language> languages = new ArrayList<>();

    Collection<User> readers = new ArrayList<>();

    public BookTo() {
    }

    public BookTo(Long id) {
        this.id = id;
    }

    public BookTo(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public BookTo(Author author, String title, String genre, int year) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public BookTo(Long id, Author author, String title, String genre, int year) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public BookTo(Long id, Author author, String title, String genre, int year,
                  Collection<Language> languages) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.languages = languages;
    }

    public BookTo(Long id, Author author, String title, String genre, int year,
                  Collection<Language> languages, Collection<User> readers) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.languages = languages;
        this.readers = readers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Collection<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Collection<Language> languages) {
        this.languages = languages;
    }

    public Collection<User> getReaders() {
        return readers;
    }

    public void setReaders(Collection<User> readers) {
        this.readers = readers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTo bookTo = (BookTo) o;
        return year == bookTo.year && Objects.equals(id, bookTo.id) && Objects.equals(author, bookTo.author) && Objects.equals(title, bookTo.title) && Objects.equals(genre, bookTo.genre) && Objects.equals(languages, bookTo.languages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, genre, year, languages);
    }

    @Override
    public String toString() {
        return "BookTo{" +
               "id=" + id +
               ", author=" + author +
               ", title='" + title + '\'' +
               ", genre='" + genre + '\'' +
               ", year=" + year +
               ", languages=" + languages +
               ", readers=" + readers +
               '}';
    }
}
