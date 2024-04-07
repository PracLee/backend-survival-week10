package kr.megaptera.backendsurvivalweek10.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private final AccessTokenGenerator accessTokenGenerator;

    public AccessTokenService(AccessTokenGenerator accessTokenGenerator) {
        this.accessTokenGenerator = accessTokenGenerator;
    }

    public Authentication authenticate(String accessToken) {
        if (!accessTokenGenerator.verify(accessToken)){
            return null;
        }

        return
    }
}
