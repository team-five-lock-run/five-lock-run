package team.project.fiverockrun.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.project.fiverockrun.domain.auth.dto.request.AuthRequestDto;
import team.project.fiverockrun.domain.auth.dto.response.AuthResponseDto;
import team.project.fiverockrun.domain.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public AuthResponseDto.Signup signup(@Valid @RequestBody AuthRequestDto.Signup signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/auth/signin")
    public AuthResponseDto.Signin signin(@Valid @RequestBody AuthRequestDto.Signin signinRequestDto) {
        return authService.signin(signinRequestDto);
    }
}
