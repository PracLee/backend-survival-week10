package kr.megaptera.backendsurvivalweek10.application.users;

import io.hypersistence.tsid.TSID;
import kr.megaptera.backendsurvivalweek10.infrastructure.UserDetailsDao;
import kr.megaptera.backendsurvivalweek10.security.AccessTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final AccessTokenGenerator accessTokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsDao userDetailsDao;

    public CreateUserService(AccessTokenGenerator accessTokenGenerator,
                             PasswordEncoder passwordEncoder,
                             UserDetailsDao userDetailsDao) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsDao = userDetailsDao;
    }

    public String createUser(String username, String password) {
        if (userDetailsDao.existsByUsername(username)) {
            throw new RuntimeException("EXISTS!");
        }
        String id = TSID.Factory.getTsid().toString();
        String encodedPassword = passwordEncoder.encode(password);
        String accessToken = accessTokenGenerator.generate(id);

        userDetailsDao.addUser(id, username, encodedPassword);
        userDetailsDao.addAccessToken(id, accessToken);
        return accessToken;
    }
}
