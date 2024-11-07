package org.aston.application.repository;

import org.aston.application.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b " +
           "where b.title = ?1 and b.genre = ?2 and b.year = ?3")
    Optional<Book> findByTitleAndGenreAndYear(@NonNull String title,
                                              @Nullable String genre,
                                              @Nullable int year);

    @Transactional
    @Modifying
    @Query("""
            update Book b
            set b.title = ?2, b.genre = ?3, b.year = ?4
            where b.id = ?1
            """)
    void updateById(Long id, String title, String genre, int year);

}
