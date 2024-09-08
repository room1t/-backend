package se.sowl.roomitdomain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "userrole")
public class UserRole {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;

    @OneToOne(mappedBy = "userRole")
    private User user;

    @Builder
    public UserRole(Long id,String role, User user) {
        this.id = id;
        this.role = role;
        this.user = user;
    }
}
