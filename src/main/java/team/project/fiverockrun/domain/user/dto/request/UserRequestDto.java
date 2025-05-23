    package team.project.fiverockrun.domain.user.dto.request;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;
    import lombok.Getter;
    import lombok.RequiredArgsConstructor;

    public class UserRequestDto {

        @Getter
        @RequiredArgsConstructor
        public static class Edit {
            @NotBlank(message = "이메일은 필수입니다.")
            @Pattern(
                    regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                    message = "올바른 이메일 형식이 아닙니다."
            )
            private final String email;

            @Pattern(regexp = "^0\\d{1,2}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
            private final String phoneNumber;

            @NotBlank(message = "사용자 이름은 필수입니다.")
            @Size(max = 8, message = "이름은 최대 8글자까지 가능합니다.")
            private final String name;

            @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다")
            @Pattern(
                    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$",
                    message = "비밀번호는 숫자, 대문자, 소문자, 특수문자를 모두 포함해야 합니다."
            )
            private final String newPassword;
            private final String previousPassword;
        }

        @Getter
        @RequiredArgsConstructor
        public static class Delete {
            @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다")
            @Pattern(
                    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$",
                    message = "비밀번호는 숫자, 대문자, 소문자, 특수문자를 모두 포함해야 합니다."
            )
            private final String password;
        }
    }
