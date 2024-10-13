package org.aston.application.repository;

import org.aston.application.config.ConnectionPool;
import org.aston.application.entity.Author;
import org.aston.application.entity.Book;
import org.aston.application.exception.DaoException;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class BookRepository implements Repository<Book> {

    private final AuthorRepository authorRepository;

    public static final String SQL_GET_ALL = """
            SELECT id, author_id, title, genre, year
            FROM book
            """;

    public static final String SQL_GET_BY_ID = """
            SELECT id, author_id, title, genre, year
            FROM book
            WHERE id=?
            """;

    public static final String SQL_FIND = """
            SELECT id, author_id, title, genre, year
            FROM book
            WHERE
            (? OR id=?) AND
            (? OR title=?) AND
            (? OR genre=?) AND
            (? OR year=?);
            """;

    public static final String SQL_CREATE = """
             INSERT INTO book (author_id, title, genre, year)
             VALUES (?,?,?,?)
            """;

    public static final String SQL_UPDATE = """
            UPDATE book
               SET author_id=?, title=?, genre=?, year=?
             WHERE id = ?;
            """;

    public static final String SQL_DELETE = """
            DELETE
            FROM book
            WHERE id=?
            """;

    public BookRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> getAll() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
            return buildBookStream(preparedStatement).toList();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Book> get(long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long bookId = resultSet.getLong("id");
            return Optional.of(new Book(
                    bookId,
                    getBookAuthor(bookId),
                    resultSet.getString("title"),
                    resultSet.getString("genre"),
                    resultSet.getInt("year")
            ));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Stream<Book> find(Book book) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND)) {

            Long id = book.getId();
            preparedStatement.setBoolean(1, Objects.isNull(id));
            preparedStatement.setLong(2, Objects.nonNull(id) ? id : 0L);

            String title = book.getTitle();
            preparedStatement.setBoolean(3, Objects.isNull(title));
            preparedStatement.setString(4, Objects.nonNull(title) ? title : "");

            String genre = book.getGenre();
            preparedStatement.setBoolean(5, Objects.isNull(genre));
            preparedStatement.setString(6, Objects.nonNull(genre) ? genre : "");

            int year = book.getYear();
            preparedStatement.setBoolean(7, year == 0);
            preparedStatement.setInt(8, year);

            return buildBookStream(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Book book) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, book.getAuthor().getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getYear());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            book.setId(generatedKeys.getLong(1));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Book book) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setLong(1, book.getAuthor().getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getYear());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Book book) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Stream<Book> buildBookStream(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Stream.Builder<Book> books = Stream.builder();
        while (resultSet.next()) {
            long bookId = resultSet.getLong("id");
            books.add(new Book(
                    bookId,
                    getBookAuthor(bookId),
                    resultSet.getString("title"),
                    resultSet.getString("genre"),
                    resultSet.getInt("year")
            ));
        }
        return books.build();
    }

    private Author getBookAuthor(long bookId) {
        Author bookAuthor = null;
        Optional<Author> optionalAuthor = authorRepository.get(bookId);
        if (optionalAuthor.isPresent()) {
            bookAuthor = optionalAuthor.get();
        }
        return bookAuthor;
    }
}
