package se.sowl.roomitapi.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.user.domain.User;
import se.sowl.roomitdomain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getList() {
        return userRepository.findAll();
    }
}
