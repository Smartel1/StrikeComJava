package ru.smartel.strike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.ClientVersion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientVersionRepository extends JpaRepository<ClientVersion, Integer> {
    Optional<ClientVersion> getByVersionAndClientId(String version, String clientId);
    List<ClientVersion> findAllByIdGreaterThanAndClientId(int id, String clientId);
}
