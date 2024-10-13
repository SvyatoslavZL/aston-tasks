package org.aston.application.service;

import org.aston.application.dto.UserTo;
import org.aston.application.entity.User;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    private final Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public void create(UserTo userTo) {
        userRepository.create(EntityMapper.from(userTo));
    }

    public Collection<UserTo> getAll() {
        return userRepository.getAll().stream().map(EntityMapper::from).toList();
    }

    public Optional<UserTo> get(long id) {
        User userPattern = new User(id);
        return userRepository.find(userPattern).map(EntityMapper::from).findFirst();
    }

    public Optional<UserTo> find(String login, String password) {
        User userPattern = new User(login, password);
        return userRepository.find(userPattern).map(EntityMapper::from).findAny();
    }

    public void update(UserTo userTo) {
        userRepository.update(EntityMapper.from(userTo));
    }

    public void delete(UserTo userTo) {
        userRepository.delete(EntityMapper.from(userTo));
    }
}
