package se.sowl.roomitapi.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.repository.SpaceRepository;
import se.sowl.roomitdomain.space.spaceDto.SpaceResponseDto;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;

    public Page<SpaceResponseDto> getSpaces(Pageable pageable) {
        Page<Space> spaces = spaceRepository.findAll(pageable);
        return spaces.map(SpaceResponseDto::toDTO);
    }
}
