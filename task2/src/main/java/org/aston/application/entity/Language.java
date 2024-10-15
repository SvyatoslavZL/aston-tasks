package org.aston.application.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "language")
@SuppressWarnings("unused")
public class Language implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_language",
            joinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    private Collection<Book> books = new ArrayList<>();

    public Language() {
    }

    public Language(Long id) {
        this.id = id;
    }

    public Language(String name) {
        this.name = name;
    }

    public Language(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Language(Long id, String name, Collection<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
        Language language = (Language) o;
        return Objects.equals(name, language.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Language{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", books=" + books +
               '}';
    }
}
