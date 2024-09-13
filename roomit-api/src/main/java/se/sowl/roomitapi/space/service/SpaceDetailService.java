package se.sowl.roomitapi.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceDetailService {
    @Autowired
    SpaceDetailRepository spaceDetailRepository;

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

}
