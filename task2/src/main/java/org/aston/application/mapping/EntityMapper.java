package org.aston.application.mapping;

import org.aston.application.dto.AuthorTo;
import org.aston.application.dto.BookTo;
import org.aston.application.dto.LanguageTo;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.Author;
import org.aston.application.entity.Book;
import org.aston.application.entity.Language;
import org.aston.application.entity.User;

public class EntityMapper {


    private EntityMapper() {
    }

    public static User from(UserTo userTo) {
        return new User(
                userTo.getId(),
                userTo.getLogin(),
                userTo.getPassword(),
                userTo.getRole(),
                userTo.getBooks());
    }

    public static UserTo from(User user) {
        return new UserTo(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getBooks()
        );
    }

    public static Author from(AuthorTo authorTo) {
        return new Author(
                authorTo.getId(),
                authorTo.getFirstName(),
                authorTo.getLastName(),
                authorTo.getBooks()
        );
    }

    public static AuthorTo from(Author author) {
        return new AuthorTo(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBooks()
        );
    }

    public static Book from(BookTo bookTo) {
        return new Book(
                bookTo.getId(),
                bookTo.getAuthor(),
                bookTo.getTitle(),
                bookTo.getGenre(),
                bookTo.getYear(),
                bookTo.getLanguages(),
                bookTo.getReaders()
        );
    }

    public static BookTo from(Book book) {
        return new BookTo(
                book.getId(),
                book.getAuthor(),
                book.getTitle(),
                book.getGenre(),
                book.getYear(),
                book.getLanguages(),
                book.getReaders()
        );
    }

    public static Language from(LanguageTo languageTo) {
        return new Language(
                languageTo.getId(),
                languageTo.getName(),
                languageTo.getBooks()
        );
    }

    public static LanguageTo from(Language language) {
        return new LanguageTo(
                language.getId(),
                language.getName(),
                language.getBooks()
        );
    }

}
