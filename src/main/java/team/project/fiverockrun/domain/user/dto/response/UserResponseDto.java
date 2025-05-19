package team.project.fiverockrun.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.project.fiverockrun.domain.user.enums.UserRole;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Get {
        private final String email;
        private final String phoneNumber;
        private final String name;
        private final UserRole userRole;
    }
}
