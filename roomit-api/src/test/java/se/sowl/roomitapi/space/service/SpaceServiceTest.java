package se.sowl.roomitapi.space.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.sowl.roomitdomain.space.repository.SpaceRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpaceServiceTest {
    @Autowired
    private SpaceService spaceService;

    @Autowired
    private SpaceRepository spaceRepository;

    // 테스트가 끝날 때 마다 실행되는 어노테이션.
    // 테스트가 끝날 때 마다 데이터를 삭제해야 다른 테스트코드를 실행했을 떄 영향을 받지 않게끔 할 수 있슴다.
    @AfterEach
    void tearDown() {
        spaceRepository.deleteAllInBatch();
    }

    // NESTED 란 테스트 클래스를 만들어서 테스트 코드를 구조화하는 방법.
    // 서비스 메서드 테스트를 만들때 하나의 메서드에서 여러개의 변수 상황이 있을 수 있으므로 NESTED 를 사용해 테스트 코드를 구조화!
    @Nested
    @DisplayName("공간 목록 조회")
    class getSpaces {

        // TODO: STEP2: 서비스 코드에서 발생할 수 있는 모든 변수 상황을 테스트코드로 옮긴다.
        // TIP: 테스트 설명은 사용자 관점과 도메인 용어를 사용해서 자세하게 적는다.
        // EX: 사용자가 공간 목록을 조회했지만 공간이 없을 때, 빈 목록을 응답한다.
        // EX: 사용자가 공간 목록을 조회하면 조회 조건에 맞게 응답한다.
        @Test
        @DisplayName("사용자가 공간 목록을 조회했지만 공간이 없을 때, 빈 목록을 응답한다")
        void emptySpace() {
            // given
            // when
            // then
        }

        @Test
        @DisplayName("사용자가 공간 목록을 조회하면 조회 조건에 맞게 응답한다")
        void condition() {
            // given
            // when
            // then
        }
    }
}
