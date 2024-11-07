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
public class AuthorTo {

    Long id;

    String firstName;

    String lastName;

    List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorTo authorTo = (AuthorTo) o;
        return Objects.equals(firstName, authorTo.firstName)
               && Objects.equals(lastName, authorTo.lastName)
               && Objects.equals(books, authorTo.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, books);
    }

}
