package se.sowl.roomitapi.space.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import se.sowl.roomitapi.fixture.SpaceFixture;
import se.sowl.roomitapi.fixture.UserFixture;
import se.sowl.roomitdomain.oauth.domain.OAuth2Provider;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;
import se.sowl.roomitdomain.space.repository.SpaceRepository;
import se.sowl.roomitdomain.space.spaceDto.SpaceResponseDto;
import se.sowl.roomitdomain.user.domain.Provider;
import se.sowl.roomitdomain.user.domain.User;
import se.sowl.roomitdomain.user.domain.UserRole;
import se.sowl.roomitdomain.user.repository.ProviderRepository;
import se.sowl.roomitdomain.user.repository.UserRepository;
import se.sowl.roomitdomain.user.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpaceServiceTest {
    @Autowired
    private SpaceService spaceService;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SpaceDetailService spaceDetailService;

    @Autowired
    private SpaceDetailRepository spaceDetailRepository;

    // 테스트가 끝날 때 마다 실행되는 어노테이션.
    // 테스트가 끝날 때 마다 데이터를 삭제해야 다른 테스트코드를 실행했을 떄 영향을 받지 않게끔 할 수 있슴다.
    @AfterEach
    void tearDown() {
        spaceDetailRepository.deleteAllInBatch();
        spaceRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @AfterAll
    void cleanUp() {
        spaceDetailRepository.deleteAllInBatch();
        spaceRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        providerRepository.deleteAllInBatch();
        userRoleRepository.deleteAllInBatch();
    }

    // NESTED 란 테스트 클래스를 만들어서 테스트 코드를 구조화하는 방법.
    @BeforeAll
    void setUp() {
        Provider google = new Provider(OAuth2Provider.GOOGLE.getRegistrationId());
        Provider kakao = new Provider(OAuth2Provider.KAKAO.getRegistrationId());
        Provider naver = new Provider(OAuth2Provider.NAVER.getRegistrationId());

        UserRole user = new UserRole("user");
        UserRole owner = new UserRole("owner");
        UserRole admin = new UserRole("admin");

        providerRepository.saveAll(List.of(google, kakao, naver));
        userRoleRepository.saveAll(List.of(user,owner,admin));
    }

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
            // TODO: STEP3: 테스트 코드를 작성한다.
        void emptySpace() {
            // TODO: (given)은 테스트하고자 하는 메서드를 실행하기 전에 하는 사전작업,
            // TODO: 주로 테스트할 객체를 생성하거나 초기화하는 작업을 한다.
            // given

            Pageable pageable = PageRequest.of(0,10);

            // TODO: (when)은 테스트하고자 하는 메서드를 실행하는 작업
            // when
            Page<SpaceResponseDto> spaces = spaceService.getSpaces(pageable);

            // TODO: (then)은 테스트하고자 하는 메서드를 실행한 결과를 검증하는 작업!
            // then
            assertThat(spaces.getTotalElements()).isEqualTo(0);
        }

        @Test
        @Transactional
        @DisplayName("사용자가 공간 목록을 조회하면 조회 조건에 맞게 응답한다")
        void condition() {
            // given

            String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
            Provider provider = providerRepository.findByName(providerRegistrationId);

            UserRole userRole = userRoleRepository.findByRole("user");
            User user = userRepository.save(UserFixture.createUser("박동준", "dj", "dj@test.com", provider, userRole));

            PageRequest request = PageRequest.of(0, 10);

            // TODO: STEP4:(동준형이 했으면 하는 것) Space 엔티티 여러개 생성.

            List<Space> spaces = new ArrayList<>();
            List<SpaceDetail> spaceDetails = new ArrayList<>();

            for(int i=0;i<10;i++){ // 10개의 space,
                Space space = Space.builder()
                    .name("이디야")
                    .description("성공회대 앞 2층")
                    .address("구로구 어쩌구")
                    .maxCapacity(30)
                    .owner(user)
                    .build();

                SpaceDetail spaceDetail;

                for(int j=0;j<3;j++) { // 30개의 spaceDeatil
                    spaceDetail = SpaceDetail.builder()
                        .space(space)
                        .name("테이블1")
                        .description("원형입니다")
                        .capacity(8)
                        .pricePerHour(1.0)
                        .build();

                    spaceDetails.add(spaceDetail);
                }

                space.addSpaceDetails(spaceDetails);
                spaces.add(space);
            }

            // TODO: STEP5: postRepository 에 저장
            spaceRepository.saveAll(spaces);
            Pageable pageable = PageRequest.of(0,10);

            // when
            Page<SpaceResponseDto> result = spaceService.getSpaces(pageable);
            List<SpaceResponseDto> content = result.getContent();
            assertThat(content.get(0)).isExactlyInstanceOf(SpaceResponseDto.class);
            // spacedetaildto -> 만들고 클래스 검증까지

            // then


            // TODO: STEP6: 조회된 post 개수 및 데이터 검증
            assertThat(result.getTotalElements()).isEqualTo(10);
        }

    }
}
