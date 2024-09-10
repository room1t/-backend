package se.sowl.roomitdomain.user.repository;

import org.springframework.data.repository.CrudRepository;
import se.sowl.roomitdomain.user.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
