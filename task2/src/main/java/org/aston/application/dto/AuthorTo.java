package org.aston.application.dto;

import org.aston.application.entity.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class AuthorTo {

    Long id;

    String firstName;

    String lastName;

    Collection<Book> books = new ArrayList<>();

    public AuthorTo() {
    }

    public AuthorTo(Long id) {
        this.id = id;
    }

    public AuthorTo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorTo(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorTo(Long id, String firstName, String lastName, Collection<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        AuthorTo authorTo = (AuthorTo) o;
        return Objects.equals(firstName, authorTo.firstName) && Objects.equals(lastName, authorTo.lastName) && Objects.equals(books, authorTo.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, books);
    }

    @Override
    public String toString() {
        return "AuthorTo{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", books=" + books +
               '}';
    }
}
