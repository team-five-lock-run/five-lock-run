package team.project.fiverockrun.domain.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.project.fiverockrun.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .map(CustomUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
