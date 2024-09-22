package se.sowl.roomitapi.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.sowl.roomitapi.space.exception.SpaceNotExistException;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;
import se.sowl.roomitdomain.space.repository.SpaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpaceDetailService {
    private final SpaceRepository spaceRepository;

    public List<SpaceDetailResponseDto> getSpaceDetail(Long spaceId) {
        Optional<Space> space = spaceRepository.findWithDetailsById(spaceId);
        if (space.isEmpty()) {
            throw new SpaceNotExistException("공간 정보를 찾을 수 없어요.");
        }
        List<SpaceDetail> spaceDetails = space.get().getSpaceDetails();
        return spaceDetails.stream().map(SpaceDetailResponseDto::toDTO).toList();
    }
}
