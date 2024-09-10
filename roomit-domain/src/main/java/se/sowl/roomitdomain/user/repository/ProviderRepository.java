package se.sowl.roomitdomain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sowl.roomitdomain.user.domain.Provider;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Provider findByName(String name);
}
