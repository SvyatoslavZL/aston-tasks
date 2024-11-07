package org.aston.application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "author")
public class Author implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firs_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName)
               && Objects.equals(lastName, author.lastName)
               && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, books);
    }

}
