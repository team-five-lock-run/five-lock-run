package team.project.fiverockrun.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.config.JwtUtil;
import team.project.fiverockrun.common.config.PasswordEncoder;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.auth.dto.request.AuthRequestDto;
import team.project.fiverockrun.domain.auth.dto.response.AuthResponseDto;
import team.project.fiverockrun.domain.user.entity.User;
import team.project.fiverockrun.domain.user.enums.UserRole;
import team.project.fiverockrun.domain.user.repository.UserRepository;

import static team.project.fiverockrun.domain.auth.exception.AuthError.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDto.Signup signup(AuthRequestDto.Signup signupRequest) {
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(user -> {
            if (user.isDeleted()) {
                throw new BaseException(DELETED_USER);
            }
            throw new BaseException(DUPLICATE_EMAIL);
        });

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber(),
                signupRequest.getName(),
                encodedPassword,
                userRole
        );
        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), savedUser.getPhoneNumber(), savedUser.getName(), userRole);

        return new AuthResponseDto.Signup(bearerToken);
    }

    public AuthResponseDto.Signin signin(AuthRequestDto.Signin signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new BaseException(USER_NOT_FOUND));

        if(user.isDeleted()) {
            throw new BaseException(DELETED_USER);
        }

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new BaseException(WRONG_PASSWORD);
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getPhoneNumber(), user.getName(), user.getUserRole());

        return new AuthResponseDto.Signin(bearerToken);
    }
}
