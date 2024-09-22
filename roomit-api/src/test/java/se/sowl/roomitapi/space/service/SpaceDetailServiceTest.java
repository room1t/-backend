package se.sowl.roomitapi.space.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.sowl.roomitapi.fixture.UserFixture;
import se.sowl.roomitdomain.oauth.domain.OAuth2Provider;
import se.sowl.roomitdomain.space.SpaceDetailDto.SpaceDetailResponseDto;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;
import se.sowl.roomitdomain.space.repository.SpaceRepository;
import se.sowl.roomitdomain.user.domain.Provider;
import se.sowl.roomitdomain.user.domain.User;
import se.sowl.roomitdomain.user.domain.UserRole;
import se.sowl.roomitdomain.user.repository.ProviderRepository;
import se.sowl.roomitdomain.user.repository.UserRepository;
import se.sowl.roomitdomain.user.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpaceDetailServiceTest {

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

    @AfterEach
    void tearDown() {
        spaceDetailRepository.deleteAllInBatch();
        spaceRepository.deleteAllInBatch(); // 자식. user_id _> owner_id로 쓰고있다.
        userRepository.deleteAllInBatch(); // 부모. user_id 를 가지고있다.
    }

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

    @Nested
    class GetSpaceDetail {
//        @Test
//        void getSpaceDetail() {
//            // given
//            User user = getUser();
//            Space space = spaceRepository.save(Space.builder().name("이디야").description("성공회대 앞 2층").address("구로구 어쩌구").maxCapacity(30).owner(user).build());
//            List<SpaceDetail> spaceDetails = new ArrayList<>();
//            for(int j=0;j<3;j++) spaceDetails.add(SpaceDetail.builder().space(space).name("테이블" + j).description("원형입니다").capacity(8).pricePerHour(1.0).build());
//            space.addSpaceDetails(spaceDetails);
//            spaceRepository.save(space);
//            // when
//            // then
//        }

        @Test
        @DisplayName("사용자가 세부 공간을 조회했지만 세부 공간이 없을 경우, 빈 세부 목록을 응답한다.")
        void emptySpaceDetail() {
            //given
            String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
            Provider provider = providerRepository.findByName(providerRegistrationId);
            UserRole userRole = userRoleRepository.findByRole("user");
            User user = userRepository.save(UserFixture.createUser("dj","djdj", "dj@email",provider,userRole));
            Space space = Space.builder()
                .name("이디야")
                .description("2층에 있음")
                .address("구로구 ~")
                .maxCapacity(30)
                .owner(user)
                .build();
            spaceRepository.save(space);
            Long spaceId = space.getId();

            //when
            List<SpaceDetailResponseDto> spaceDetailResponseDtos = spaceDetailService.getSpaceDetail(spaceId);

            //then

            assertThat(spaceDetailResponseDtos).isEqualTo(new ArrayList<SpaceDetailResponseDto>());
        }

        @Test
        @DisplayName("사용자가 세부 공간을 조회하면 조회 조건에 맞게 응답한다")
        void getSpaceDetail2() {
            //given
            String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
            Provider provider = providerRepository.findByName(providerRegistrationId);
            UserRole userRole = userRoleRepository.findByRole("user");
            User user = userRepository.save(UserFixture.createUser("박동준", "dj", "dj@test.com", provider, userRole));
            Space space = spaceRepository.save(Space.builder().name("이디야").description("2층에 있음").address("구로구 ~").maxCapacity(30).owner(user).build());
            List<SpaceDetail> spaceDetails = new ArrayList<>();
            for(int i=0;i<3;i++){
                SpaceDetail detail = SpaceDetail.builder().space(space).name("방" + (i + 1)).capacity(4).description("원형탁자가 있음").pricePerHour(1.3).build();
                spaceDetails.add(detail);
            }
            spaceDetailRepository.saveAll(spaceDetails);
            space.addSpaceDetails(spaceDetails);
            spaceRepository.save(space);

            //when
            Long spaceId = space.getId();
            List<SpaceDetailResponseDto> spaceDetailResponseDtos = spaceDetailService.getSpaceDetail(spaceId);

            //then
            assertThat(spaceDetailResponseDtos.size()).isEqualTo(3);
            assertThat(spaceDetailResponseDtos.get(0)).isExactlyInstanceOf(SpaceDetailResponseDto.class);
        }

    }

    private User getUser() {
        String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
        Provider provider = providerRepository.findByName(providerRegistrationId);
        UserRole userRole = userRoleRepository.findByRole("user");
        return userRepository.save(UserFixture.createUser("박동준", "dj", "dj@test.com", provider, userRole));
    }
}
