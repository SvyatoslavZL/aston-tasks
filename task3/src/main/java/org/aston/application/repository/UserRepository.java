package org.aston.application.repository;

import org.aston.application.dto.Role;
import org.aston.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
           "where u.login = ?1 and u.password = ?2")
    Optional<User> findByLoginAndPassword(@NonNull String login, @Nullable String password);

    @Transactional
    @Modifying
    @Query("""
            update User u
            set u.login = ?2, u.password = ?3, u.role = ?4
            where u.id = ?1
            """)
    void updateById(Long id, String login, String password, Role role);

}
