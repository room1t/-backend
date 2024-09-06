package se.sowl.roomitdomain.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sowl.roomitdomain.space.domain.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
}
