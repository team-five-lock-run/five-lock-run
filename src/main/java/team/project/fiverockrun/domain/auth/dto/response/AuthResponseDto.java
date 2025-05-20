package team.project.fiverockrun.domain.auth.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.project.fiverockrun.domain.user.enums.UserRole;

@Getter
@RequiredArgsConstructor
public class AuthResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Signup {
        private final String bearerToken;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Signin {
        private final String bearerToken;
    }
}
