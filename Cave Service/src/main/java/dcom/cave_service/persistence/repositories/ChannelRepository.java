package dcom.cave_service.persistence.repositories;

import dcom.cave_service.persistence.entities.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {
}
