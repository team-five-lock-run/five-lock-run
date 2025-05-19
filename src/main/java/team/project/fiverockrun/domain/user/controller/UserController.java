package team.project.fiverockrun.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team.project.fiverockrun.domain.user.dto.response.UserResponseDto;
import team.project.fiverockrun.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //사용자 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto.Get> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    //사용자 수정

    //사용자 삭제

}
