package se.sowl.roomitdomain.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.SpaceDetail;

import java.util.List;

public interface SpaceDetailRepository extends JpaRepository<SpaceDetail, Long> {
    List<SpaceDetail> findAll();

}
