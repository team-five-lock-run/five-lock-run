package team.project.fiverockrun.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.fiverockrun.common.config.PasswordEncoder;
import team.project.fiverockrun.common.exception.BaseException;
import team.project.fiverockrun.domain.user.dto.request.UserRequestDto;
import team.project.fiverockrun.domain.user.dto.response.UserResponseDto;
import team.project.fiverockrun.domain.user.entity.User;
import team.project.fiverockrun.domain.user.exception.UserError;
import team.project.fiverockrun.domain.user.repository.UserRepository;

import static team.project.fiverockrun.domain.user.exception.UserError.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //유저 조회
    @Transactional(readOnly = true)
    public UserResponseDto.Get getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        return new UserResponseDto.Get(user.getEmail(), user.getPhoneNumber(), user.getName(), user.getUserRole());
    }

    //유저 수정
    @Transactional
    public UserResponseDto.Edit editUser(UserRequestDto.Edit requestDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        //이름, 이메일, 전화번호 업데이트
        if(requestDto.getName() != null){
            user.updateName(requestDto.getName());
        }
        if(requestDto.getEmail() != null){
            user.updateEmail(requestDto.getEmail());
        }
        if(requestDto.getPhoneNumber() != null){
            user.updatePhoneNumber(requestDto.getPhoneNumber());
        }

        //비번 업데이트
        if(requestDto.getNewPassword() != null) {
            if (passwordEncoder.matches(requestDto.getPreviousPassword(), user.getPassword())) {
                String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
                user.updatePassword(encodedPassword);
            } else {
                throw new BaseException(WRONG_PASSWORD);
            }
        }
        return new UserResponseDto.Edit(user.getEmail(), user.getPhoneNumber(), user.getName());
    }

    //유저 삭제
    @Transactional
    public void deleteUser(UserRequestDto.Delete requestDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        if(user.isDeleted()) {
            throw new BaseException(DELETED_USER);
        }

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new BaseException(WRONG_PASSWORD);
        }
        user.deleteUser();
    }
}