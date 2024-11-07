package org.aston.application.repository;

import org.aston.application.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a " +
           "where a.firstName = ?1 and a.lastName = ?2")
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    @Transactional
    @Modifying
    @Query("""
            update Author a
            set a.firstName = ?2, a.lastName = ?3
            where a.id = ?1
            """)
    void updateById(Long id, String firstName, String lastName);

}
