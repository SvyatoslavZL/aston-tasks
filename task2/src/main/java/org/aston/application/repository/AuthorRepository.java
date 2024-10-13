package org.aston.application.repository;

import org.aston.application.config.ConnectionPool;
import org.aston.application.entity.Author;
import org.aston.application.exception.DaoException;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AuthorRepository implements Repository<Author> {

    public static final String SQL_GET_ALL = """
            SELECT id, firs_name, last_name
            FROM author
            """;

    public static final String SQL_GET_BY_ID = """
            SELECT id, firs_name, last_name
            FROM author
            WHERE id=?
            """;

    public static final String SQL_FIND = """
            SELECT id, firs_name, last_name
            FROM author
            WHERE
            (? OR id=?) AND
            (? OR firs_name=?) AND
            (? OR last_name=?);
            """;

    public static final String SQL_CREATE = """
             INSERT INTO author (firs_name, last_name)
             VALUES (?,?)
            """;

    public static final String SQL_UPDATE = """
            UPDATE author
               SET firs_name=?, last_name=?
             WHERE id=?;
            """;

    public static final String SQL_DELETE = """
            DELETE
            FROM author
            WHERE id=?
            """;

    @Override
    public List<Author> getAll() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
            return buildAuthorStream(preparedStatement).toList();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Author> get(long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(new Author(
                    resultSet.getLong("id"),
                    resultSet.getString("firs_name"),
                    resultSet.getString("last_name")
            ));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Stream<Author> find(Author author) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND)) {

            Long id = author.getId();
            preparedStatement.setBoolean(1, Objects.isNull(id));
            preparedStatement.setLong(2, Objects.nonNull(id) ? id : 0L);

            String firstName = author.getFirstName();
            preparedStatement.setBoolean(3, Objects.isNull(firstName));
            preparedStatement.setString(4, Objects.nonNull(firstName) ? firstName : "");

            String lastName = author.getLastName();
            preparedStatement.setBoolean(5, Objects.isNull(lastName));
            preparedStatement.setString(6, Objects.nonNull(lastName) ? lastName : "");

            return buildAuthorStream(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Author author) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            author.setId(generatedKeys.getLong(1));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Author author) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Author author) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Stream<Author> buildAuthorStream(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Stream.Builder<Author> authors = Stream.builder();
        while (resultSet.next()) {
            authors.add(new Author(
                    resultSet.getLong("id"),
                    resultSet.getString("firs_name"),
                    resultSet.getString("last_name")
            ));
        }
        return authors.build();
    }
}
