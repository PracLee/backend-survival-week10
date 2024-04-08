package kr.megaptera.backendsurvivalweek10.application.users;

import kr.megaptera.backendsurvivalweek10.infrastructure.UserDetailsDao;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private final UserDetailsDao userDetailsDao;

    public LogoutService(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    public void logout(String accessToken) {
        userDetailsDao.removeAccessToken(accessToken);
    }
}
