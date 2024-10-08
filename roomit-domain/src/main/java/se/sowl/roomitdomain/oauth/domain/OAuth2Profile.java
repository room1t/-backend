package se.sowl.roomitdomain.oauth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import se.sowl.roomitdomain.user.domain.Provider;
import se.sowl.roomitdomain.user.domain.User;

@Getter
@Setter
@Builder
public class OAuth2Profile {
    private String name;
    private String email;
    private Provider provider;

    public User toUser() {
        return User.builder()
            .name(name)
            .email(email)
            .provider(provider)
            .build();
    }
}
