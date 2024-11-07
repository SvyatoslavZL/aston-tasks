package org.aston.application.dto;

import lombok.*;
import org.aston.application.entity.Book;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserTo {

    Long id;

    String login;

    String password;

    Role role;

    List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo userTo = (UserTo) o;
        return Objects.equals(id, userTo.id)
               && Objects.equals(login, userTo.login)
               && Objects.equals(password, userTo.password)
               && role == userTo.role
               && Objects.equals(books, userTo.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, books);
    }

}
