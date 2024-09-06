package se.sowl.roomitdomain.oauth.domain;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import se.sowl.roomitdomain.user.domain.User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final User user;
    private final OAuth2User oauth2User;

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getName();
    }

    public Long getUserId() {
        return user.getId();
    }

    public @Size(min = 2, max = 15, message = "닉네임은 2자 이상 15자 이하여야 합니다.") String getNickname() {
        return user.getNickname();
    }
}
