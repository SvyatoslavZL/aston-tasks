package org.aston.application.service;

import lombok.AllArgsConstructor;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.User;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements CrudService<UserTo> {

    private final UserRepository userRepository;

    @Override
    public UserTo create(UserTo userTo) {
        User user = EntityMapper.mapper.from(userTo);
        User createdUser = userRepository.saveAndFlush(user);
        return EntityMapper.mapper.from(createdUser);
    }

    @Override
    public List<UserTo> getAll() {
        return userRepository.findAll()
                .stream()
                .map(EntityMapper.mapper::from)
                .toList();
    }

    @Override
    public Optional<UserTo> get(Long id) {
        return userRepository.findById(id)
                .map(EntityMapper.mapper::from);
    }

    public Optional<UserTo> get(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .map(EntityMapper.mapper::from);
    }

    @Override
    public void update(Long id, UserTo userTo) {
        userRepository.updateById(id,
                userTo.getLogin(),
                userTo.getPassword(),
                userTo.getRole()
        );
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
