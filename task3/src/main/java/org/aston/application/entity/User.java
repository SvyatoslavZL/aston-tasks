package org.aston.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aston.application.dto.Role;

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
@Table(name = "users")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private UserData userData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_book",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
               && Objects.equals(login, user.login)
               && Objects.equals(password, user.password)
               && role == user.role
               && Objects.equals(books, user.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, books);
    }

}
