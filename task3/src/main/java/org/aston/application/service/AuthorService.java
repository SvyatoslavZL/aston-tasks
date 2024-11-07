package org.aston.application.service;

import lombok.AllArgsConstructor;
import org.aston.application.dto.AuthorTo;
import org.aston.application.entity.Author;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthorService implements CrudService<AuthorTo> {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorTo create(AuthorTo authorTo) {
        Author author = EntityMapper.mapper.from(authorTo);
        Author createdAuthor = authorRepository.saveAndFlush(author);
        return EntityMapper.mapper.from(createdAuthor);
    }

    @Override
    public List<AuthorTo> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(EntityMapper.mapper::from)
                .toList();
    }

    @Override
    public Optional<AuthorTo> get(Long id) {
        return authorRepository.findById(id)
                .map(EntityMapper.mapper::from);
    }

    public Optional<AuthorTo> get(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(EntityMapper.mapper::from);
    }

    @Override
    public void update(Long id, AuthorTo authorTo) {
        authorRepository.updateById(id,
                authorTo.getFirstName(),
                authorTo.getLastName());
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

}
