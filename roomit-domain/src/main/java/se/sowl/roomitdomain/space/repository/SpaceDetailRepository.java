package se.sowl.roomitdomain.space.repository;

import org.springframework.data.repository.Repository;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.SpaceDetail;

import java.util.List;

public interface SpaceDetailRepository extends Repository<SpaceDetail, Long> {
    List<SpaceDetailResponseDto> findAll();
}
