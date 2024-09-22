package se.sowl.roomitdomain.space.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import se.sowl.roomitdomain.space.domain.Space;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    @EntityGraph(attributePaths = "spaceDetails")
    Optional<Space> findWithDetailsById(Long id);
}
