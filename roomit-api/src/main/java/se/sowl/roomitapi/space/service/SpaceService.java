package se.sowl.roomitapi.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.repository.SpaceRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    public List<Space> getSpaces(PageRequest pageRequest) {
        // TODO: STEP1: 빈 서비스를 만든다.
        // TODO: STEP7: 테스트가 성공하도록 메서드 완성하기
        List<Space> spaces = spaceRepository.findAll(pageRequest).getContent();

        return spaces;
    }
}
