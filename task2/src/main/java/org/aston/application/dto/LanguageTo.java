package org.aston.application.dto;

import org.aston.application.entity.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class LanguageTo {

    Long id;

    String name;

    Collection<Book> books = new ArrayList<>();

    public LanguageTo() {
    }

    public LanguageTo(String name) {
        this.name = name;
    }

    public LanguageTo(Long id) {
        this.id = id;
    }

    public LanguageTo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public LanguageTo(Long id, String name, Collection<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageTo that = (LanguageTo) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "LanguageTo{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", books=" + books +
               '}';
    }
}
