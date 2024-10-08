package se.sowl.roomitapi.fixture;

import se.sowl.roomitapi.oauth.domain.TestOAuth2User;
import se.sowl.roomitdomain.user.domain.Provider;
import se.sowl.roomitdomain.user.domain.User;
import se.sowl.roomitdomain.user.domain.CustomOAuth2User;
import se.sowl.roomitdomain.user.domain.UserRole;


public class UserFixture {
    public static User createUser(String name, String nickname, String email, Provider provider, UserRole userRole) {
        return User.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .provider(provider)
                .userRole(userRole)
                .build();
    }

    public static CustomOAuth2User createCustomOAuth2User(User user) {
        TestOAuth2User testOAuth2User = new TestOAuth2User(user.getName(), user.getEmail());
        return new CustomOAuth2User(user, testOAuth2User);
    }
}
