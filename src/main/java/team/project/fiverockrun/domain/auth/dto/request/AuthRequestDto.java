package team.project.fiverockrun.domain.auth.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.project.fiverockrun.domain.user.enums.UserRole;

@Getter
@RequiredArgsConstructor
public class AuthRequestDto {

    @Getter
    @RequiredArgsConstructor
    public static class Signup {
        private final String email;
        private final String phoneNumber;
        private final String name;
        private final String password;
        private final String userRole;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Signin {
        private final String email;
        private final String password;
    }
}
