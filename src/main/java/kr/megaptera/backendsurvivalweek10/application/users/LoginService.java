package kr.megaptera.backendsurvivalweek10.application.users;

import kr.megaptera.backendsurvivalweek10.dtos.LoginResultDto;
import kr.megaptera.backendsurvivalweek10.infrastructure.UserDetailsDao;
import kr.megaptera.backendsurvivalweek10.security.AccessTokenGenerator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    private final AccessTokenGenerator accessTokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsDao userDetailsDao;

    public LoginService(AccessTokenGenerator accessTokenGenerator,
                        PasswordEncoder passwordEncoder,
                        UserDetailsDao userDetailsDao) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsDao = userDetailsDao;
    }

    public LoginResultDto login(String username, String password) {
        return userDetailsDao.findByUsername(username)
                .filter(userDetails -> passwordEncoder.matches(
                        password, userDetails.getPassword()))
                .map(userDetails -> {
                    String userId = userDetails.getUsername();
                    List<GrantedAuthority> userRoles = new ArrayList<>(userDetails.getAuthorities());
                    List<String> roles = userRoles.stream().map((GrantedAuthority::getAuthority)).toList();
                    String accessToken = accessTokenGenerator.generate(userId);
                    userDetailsDao.addAccessToken(userId, accessToken);
                    return new LoginResultDto(accessToken, roles);
                }).orElseThrow(() -> new BadCredentialsException("Login Failed"));
    }
}
