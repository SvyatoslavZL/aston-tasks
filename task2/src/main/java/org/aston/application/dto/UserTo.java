package org.aston.application.dto;

import org.aston.application.entity.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class UserTo {

    Long id;

    String login;

    String password;

    Role role;

    Collection<Book> books = new ArrayList<>();

    public UserTo() {
    }

    public UserTo(Long id) {
        this.id = id;
    }

    public UserTo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserTo(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserTo(Long id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserTo(Long id, String login, String password, Role role, Collection<Book> books) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        UserTo userTo = (UserTo) o;
        return Objects.equals(id, userTo.id) && Objects.equals(login, userTo.login) && Objects.equals(password, userTo.password) && role == userTo.role && Objects.equals(books, userTo.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, books);
    }

    @Override
    public String toString() {
        return "UserTo{" +
               "id=" + id +
               ", login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", role=" + role +
               ", books=" + books +
               '}';
    }
}
