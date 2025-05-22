package team.project.fiverockrun.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.project.fiverockrun.domain.auth.security.CustomUserPrincipal;
import team.project.fiverockrun.domain.user.dto.request.UserRequestDto;
import team.project.fiverockrun.domain.user.dto.response.UserResponseDto;
import team.project.fiverockrun.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //사용자 조회 (본인)
    @GetMapping("/users")
    public ResponseEntity<UserResponseDto.Get> getUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {
        return ResponseEntity.ok(userService.getUser(customUserPrincipal.getUser().getId()));
    }

    //사용자 수정 (본인)
    @PatchMapping("/users")
    public ResponseEntity<UserResponseDto.Edit> editUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal, @RequestBody UserRequestDto.Edit requestDto){
        return ResponseEntity.ok(userService.editUser(requestDto, customUserPrincipal.getUser().getId()));
    }

    //사용자 삭제 (본인, 회원 탈퇴)
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal, @RequestBody UserRequestDto.Delete requestDto) {
        userService.deleteUser(requestDto, customUserPrincipal.getUser().getId());
        return ResponseEntity.noContent().build();
    }

}
