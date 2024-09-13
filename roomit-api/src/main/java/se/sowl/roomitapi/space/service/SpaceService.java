package se.sowl.roomitapi.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceRepository;
import se.sowl.roomitdomain.space.spaceDto.SpaceResponseDto;

import java.util.*;

@Service
public class SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    public Page<SpaceResponseDto> getSpaces(Pageable pageable) {
        // TODO: STEP1: 빈 서비스를 만든다.
        // TODO: STEP7: 테스트가 성공하도록 메서드 완성하기

        // DTO를 만들어서 내부 도메인 객체 구성을 숨겨야됨.
        // 왜 숨기냐면 엔티티 내부에 값에 변경되면 DTO도 의도치않게 같이 변경될 가능성이있고
        // 내부에 도메인 생김새를 단순 호출로 알게되는게 좋지 않은 구조
        // 페이지네이션 처리를 할거기떄문에 페이지객체를 가지고있는 dTO 응답객체를 만들어서 반환해야할 듯?
        Page<Space> spaces = spaceRepository.findAll(pageable);
        //
        // pagenation
        //      List<page>
        //


        // SpaceDTO <- 말 그대로 엔티티를 숨기는거고.
        // SpaceResponseDTO <- 페이지네이션 조회했을 때 나온 페이지 객체를 담는 DTO
        // 페이지네이션에 대한 정보가 현재 없기때문에?

        return spaces.map(this::convertToDto);
        // List<Space> space0 space1 space2

        // space0 <- dto / space1 <- dto  / space2 <- dto
        // space 객체를 dto로 바꿔서 주게되는건가
    }

    private SpaceResponseDto convertToDto(Space space) {
        return SpaceResponseDto.builder()
                .name(space.getName())
                .description(space.getDescription())
                .address(space.getAddress())
                .maxCapacity(space.getMaxCapacity())
                .build();
    }

    public List<SpaceDetailResponseDto> getSpaceDetail(Long spaceId) {
        Space space = spaceRepository.findById(spaceId).orElse(null);

        List<SpaceDetail> spaceDetails = space.getSpaceDetails();

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
