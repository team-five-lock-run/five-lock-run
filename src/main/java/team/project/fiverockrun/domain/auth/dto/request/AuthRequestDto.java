package team.project.fiverockrun.domain.auth.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.project.fiverockrun.domain.user.enums.UserRole;

@Getter
@RequiredArgsConstructor
public class AuthRequestDto {

    @Getter
    @RequiredArgsConstructor
    public static class Signin {

    }

    @Getter
    @RequiredArgsConstructor
    public static class Signup {

    }
}
