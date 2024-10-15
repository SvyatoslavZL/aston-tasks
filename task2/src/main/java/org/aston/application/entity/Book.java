package org.aston.application.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "book")
@SuppressWarnings("unused")
public class Book implements Identifiable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "year")
    private int year;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_language",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id")
    )
    private Collection<Language> languages = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
    )
    private Collection<User> readers = new ArrayList<>();

    public Book() {
    }

    public Book(Long id) {
        this.id = id;
    }

    public Book(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public Book(Author author, String title, String genre, int year) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public Book(Long id, Author author, String title, String genre, int year) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public Book(Long id, Author author, String title, String genre, int year, Collection<Language> languages) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.languages = languages;
    }

    public Book(Long id, Author author, String title, String genre, int year, Collection<Language> languages,
                Collection<User> readers) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.languages = languages;
        this.readers = readers;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public void setLanguage(Collection<Language> languages) {
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
        Book book = (Book) o;
        return year == book.year
               && Objects.equals(id, book.id)
               && Objects.equals(author, book.author)
               && Objects.equals(title, book.title)
               && Objects.equals(genre, book.genre)
               && Objects.equals(languages, book.languages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, genre, year, languages);
    }

    @Override
    public String toString() {
        return "Book{" +
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
