package team.project.fiverockrun.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.user.dto.response.UserResponseDto;
import team.project.fiverockrun.domain.user.entity.User;
import team.project.fiverockrun.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserResponseDto.Get getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponseDto.Get(user.getEmail(), user.getPhoneNumber(), user.getName(), user.getUserRole());
    }
}
