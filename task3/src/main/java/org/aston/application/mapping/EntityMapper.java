package org.aston.application.mapping;

import org.aston.application.dto.AuthorTo;
import org.aston.application.dto.BookTo;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.Author;
import org.aston.application.entity.Book;
import org.aston.application.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {

    EntityMapper mapper = Mappers.getMapper(EntityMapper.class);

    UserTo from(User user);

    User from(UserTo userTo);

    AuthorTo from(Author author);

    Author from(AuthorTo authorTo);

    BookTo from(Book book);

    Book from(BookTo bookTo);

}
