package se.sowl.roomitapi.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;
import se.sowl.roomitdomain.space.repository.SpaceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceDetailService {
    @Autowired
    SpaceDetailRepository spaceDetailRepository;

    @Autowired
    SpaceRepository spaceRepository;

    /*
    public List<SpaceDetailResponseDto> getSpaceDetailResponseDtos() {
        List<SpaceDetail> spaceDetails = spaceDetailRepository.findAll();

        List<SpaceDetailResponseDto> spaceDetailResponseDtos = new ArrayList<>();

        for (SpaceDetail spaceDetail : spaceDetails) {
            String name = spaceDetail.getName();
            String description = spaceDetail.getDescription();
            Integer capacity = spaceDetail.getCapacity();
            Double pricePerHour = spaceDetail.getPricePerHour();

            SpaceDetailResponseDto spaceDetailResponseDto = new SpaceDetailResponseDto(name, description, capacity, pricePerHour);

            spaceDetailResponseDtos.add(spaceDetailResponseDto);
        }

        return spaceDetailResponseDtos;
    }
    */

    public List<SpaceDetailResponseDto> getSpaceDetail(Long spaceId) {
        Space space = spaceRepository.findById(spaceId).orElse(null);

        List<SpaceDetail> spaceDetails = space.getSpaceDetails();

        return getSpaceDetailResponseDto(spaceDetails);
    }

    public List<SpaceDetailResponseDto> getSpaceDetailResponseDto(List<SpaceDetail> spaceDetails) {

        List<SpaceDetailResponseDto> spaceDetailResponseDtos = new ArrayList<>();

        for(SpaceDetail spaceDetail : spaceDetails) {
            String name = spaceDetail.getName();
            String description = spaceDetail.getDescription();
            Integer capacity = spaceDetail.getCapacity();
            Double pricePerHour = spaceDetail.getPricePerHour();

            SpaceDetailResponseDto spaceDetailResponseDto = SpaceDetailResponseDto.builder()
                .name(name)
                .description(description)
                .capacity(capacity)
                .pricePerHour(pricePerHour)
                .build();

            spaceDetailResponseDtos.add(spaceDetailResponseDto);
        }

        return spaceDetailResponseDtos;
    }

}
