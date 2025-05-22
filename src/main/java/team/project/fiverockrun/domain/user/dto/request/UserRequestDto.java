package team.project.fiverockrun.domain.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserRequestDto {

    @Getter
    @RequiredArgsConstructor
    public static class Edit {
        private final String email;
        private final String phoneNumber;
        private final String name;
        private final String newPassword;
        private final String previousPassword;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Delete {
        private final String password;
    }
}
