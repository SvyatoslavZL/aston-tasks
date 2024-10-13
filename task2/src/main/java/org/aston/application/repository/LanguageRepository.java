package org.aston.application.repository;

import org.aston.application.config.ConnectionPool;
import org.aston.application.entity.Language;
import org.aston.application.exception.DaoException;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class LanguageRepository implements Repository<Language> {

    public static final String SQL_GET_ALL = """
            SELECT id, name
            FROM language
            """;

    public static final String SQL_GET_BY_ID = """
            SELECT id, name
            FROM language
            WHERE id=?
            """;

    public static final String SQL_FIND = """
            SELECT id, name
            FROM language
            WHERE
            (? OR id=?) AND
            (? OR name=?);
            """;

    public static final String SQL_CREATE = """
             INSERT INTO language (name)
             VALUES (?)
            """;

    public static final String SQL_UPDATE = """
            UPDATE language
               SET name=?
             WHERE id=?;
            """;

    public static final String SQL_DELETE = """
            DELETE
            FROM language
            WHERE id=?
            """;

    @Override
    public List<Language> getAll() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
            return buildLanguageStream(preparedStatement).toList();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Language> get(long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(new Language(
                    resultSet.getLong("id"),
                    resultSet.getString("name")
            ));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Stream<Language> find(Language language) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND)) {

            Long id = language.getId();
            preparedStatement.setBoolean(1, Objects.isNull(id));
            preparedStatement.setLong(2, Objects.nonNull(id) ? id : 0L);

            String name = language.getName();
            preparedStatement.setBoolean(3, Objects.isNull(name));
            preparedStatement.setString(4, Objects.nonNull(name) ? name : "");

            return buildLanguageStream(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Language language) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, language.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            language.setId(generatedKeys.getLong(1));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Language language) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, language.getName());
            preparedStatement.setLong(2, language.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Language language) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, language.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Stream<Language> buildLanguageStream(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Stream.Builder<Language> languages = Stream.builder();
        while (resultSet.next()) {
            languages.add(new Language(
                    resultSet.getLong("id"),
                    resultSet.getString("name")
            ));
        }
        return languages.build();
    }
}
