package ru.expanse.dao.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.expanse.dao.repository.UserRepository;
import ru.expanse.model.User;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDaoAdapter {
    private final UserRepository userRepository;

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }
}
