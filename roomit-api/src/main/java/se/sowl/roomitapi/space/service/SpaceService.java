package se.sowl.roomitapi.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceRepository;
import se.sowl.roomitdomain.space.spaceDto.SpaceResponseDto;

import java.util.*;

@Service
public class SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    public List<SpaceResponseDto> getSpaces(PageRequest pageRequest) {
        // TODO: STEP1: 빈 서비스를 만든다.
        // TODO: STEP7: 테스트가 성공하도록 메서드 완성하기

        // DTO를 만들어서 내부 도메인 객체 구성을 숨겨야됨.
        // 왜 숨기냐면 엔티티 내부에 값에 변경되면 DTO도 의도치않게 같이 변경될 가능성이있고
        // 내부에 도메인 생김새를 단순 호출로 알게되는게 좋지 않은 구조
        // 페이지네이션 처리를 할거기떄문에 페이지객체를 가지고있는 dTO 응답객체를 만들어서 반환해야할 듯?
        List<Space> spaces = spaceRepository.findAll(pageRequest).getContent();

        List<SpaceResponseDto> spaceResponseDtos = new ArrayList<>();

        for(Space space : spaces){
            SpaceResponseDto spaceResponseDto = new SpaceResponseDto();

            spaceResponseDto.setName(space.getName());
            spaceResponseDto.setDescription(space.getDescription());
            spaceResponseDto.setAddress(space.getAddress());
            spaceResponseDto.setMaxCapacity(space.getMaxCapacity());
            spaceResponseDto.setSpaceDetails(space.getSpaceDetails());

            spaceResponseDtos.add(spaceResponseDto);
        }

        return spaceResponseDtos;
    }

}