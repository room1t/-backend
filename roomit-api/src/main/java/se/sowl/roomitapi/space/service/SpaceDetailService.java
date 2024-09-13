package se.sowl.roomitapi.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;

import java.util.List;

@Service
public class SpaceDetailService {
    @Autowired
    SpaceDetailRepository spaceDetailRepository;

    public List<SpaceDetailResponseDto> getSpaceDetailResponseDtos() {
        return spaceDetailRepository.findAll();
    }

}
