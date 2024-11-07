package org.aston.application.dto;

import lombok.*;
import org.aston.application.entity.Author;
import org.aston.application.entity.User;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BookTo {

    Long id;

    Author author;

    String title;

    String genre;

    int year;

    List<User> readers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTo bookTo = (BookTo) o;
        return year == bookTo.year
               && Objects.equals(id, bookTo.id)
               && Objects.equals(author, bookTo.author)
               && Objects.equals(title, bookTo.title)
               && Objects.equals(genre, bookTo.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, genre, year);
    }

}
