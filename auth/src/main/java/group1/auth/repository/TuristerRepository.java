package group1.auth.repository;


import group1.auth.entity.Turister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TuristerRepository extends JpaRepository<Turister, Long> {

    Optional<Turister> findByUsername(String username);
}
