package se.sowl.roomitapi.reservation.service;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.sowl.roomitapi.fixture.UserFixture;
import se.sowl.roomitapi.reservation.dto.CreateReservationRequestDTO;
import se.sowl.roomitapi.reservation.exception.AlreadyReservedTimeException;
import se.sowl.roomitapi.reservation.exception.EndTimeEarlyerThanStartTimeExeption;
import se.sowl.roomitapi.reservation.exception.PastTimeReservationTimeExeption;
import se.sowl.roomitdomain.oauth.domain.OAuth2Provider;
import se.sowl.roomitdomain.reservation.domain.Reservation;
import se.sowl.roomitdomain.reservation.domain.ReservationStatus;
import se.sowl.roomitdomain.reservation.repository.ReservationRepository;
import se.sowl.roomitdomain.reservation.repository.ReservationStatusRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpaceDetailRepository spaceDetailRepository;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAllInBatch();
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
        reservationStatusRepository.deleteAllInBatch();
    }

    @BeforeAll
    void setUp() {
        Provider google = new Provider("google");
        UserRole role = new UserRole("user");
        ReservationStatus status = new ReservationStatus("예약 중");
        ReservationStatus status2 = new ReservationStatus("예약 가능");
        ReservationStatus status3 = new ReservationStatus("청소 중");

        providerRepository.save(google);
        userRoleRepository.save(role);
        reservationStatusRepository.save(status);
    }

    @Nested
    @DisplayName("예약 신청을 하는 경우")
    class tryReservation {
        @Test
        @DisplayName("이미 예약된 시간대를 포함해서 예약 신청을 하는 경우 반려되어야만 한다.")
        void containAlreadyReservationTime() {
            // given
            User fixture = UserFixture.createUser("name", "nickname", "email", providerRepository.findByName("google"), userRoleRepository.findByRole("user"));
            User user = userRepository.save(fixture);

            Space space = spaceRepository.save(Space.builder().name("이디야").description("성공회대 앞 2층").address("구로구 어쩌구").maxCapacity(30).owner(user).build());
            List<SpaceDetail> spaceDetails = new ArrayList<>();
            for (int j = 0; j < 3; j++)
                spaceDetails.add(SpaceDetail.builder().space(space).name("테이블" + j).description("원형입니다").capacity(8).pricePerHour(1.0).build());
            spaceDetailRepository.saveAll(spaceDetails);
            space.addSpaceDetails(spaceDetails);
            spaceRepository.save(space);

            Reservation reservation = Reservation.builder()
                .user(user)
                .status(reservationStatusRepository.findByName("예약 중"))
                .spaceDetail(spaceDetails.get(0))
                .startTime(LocalDateTime.parse("2024-10-16T18:36:25.400357"))
                .endTime(LocalDateTime.parse("2024-10-16T19:36:25.400357"))
                .build();
            reservationRepository.save(reservation);

            // when & then
            CreateReservationRequestDTO request = new CreateReservationRequestDTO(1L,
                LocalDateTime.parse("2024-10-16T18:36:25.400357"),
                LocalDateTime.parse("2024-10-16T19:36:25.400357"));

            //assertThat(reservationRepository.findById(reservation.getId())).isPresent();
            assertThatThrownBy(() -> reservationService.createReservation(request,user,spaceDetails.get(0)))
                .isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예약 시작시간보다 예약 종료시간이 더 빠른 경우 반려되어야만 한다.")
        void endAtIsEarlyThenStartAt() {
            //given
            String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
            Provider provider = providerRepository.findByName(providerRegistrationId);
            UserRole userRole = userRoleRepository.findByRole("user");
            User fixture = UserFixture.createUser("name", "nickname", "email", provider, userRole);
            User user = userRepository.save(fixture);

            Space space = spaceRepository.save(Space.builder().name("이디야").description("성공회대 앞 2층").address("구로구 어쩌구").maxCapacity(30).owner(user).build());
            List<SpaceDetail> spaceDetails = new ArrayList<>();
            for (int j = 0; j < 3; j++)
                spaceDetails.add(SpaceDetail.builder().space(space).name("테이블" + j).description("원형입니다").capacity(8).pricePerHour(1.0).build());
            spaceDetailRepository.saveAll(spaceDetails);
            space.addSpaceDetails(spaceDetails);
            spaceRepository.save(space);

            CreateReservationRequestDTO request = new CreateReservationRequestDTO(1L,
                LocalDateTime.parse("2024-10-22T19:36:25.400357"),
                LocalDateTime.parse("2024-10-22T17:36:25.400357"));

            //when & then
            assertThatThrownBy(() -> reservationService.createReservation(request,user,spaceDetails.get(0)))
                .isInstanceOf(EndTimeEarlyerThanStartTimeExeption.class);
        }

        @Test
        @DisplayName("현재 시간보다 과거인 예약 신청을 하는 경우 반려되어야만 한다.")
        void pastTimeReservation() {
            //given
            String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
            Provider provider = providerRepository.findByName(providerRegistrationId);
            UserRole userRole = userRoleRepository.findByRole("user");
            User fixture = UserFixture.createUser("name", "nickname", "email", provider, userRole);
            User user = userRepository.save(fixture);

            Space space = spaceRepository.save(Space.builder().name("이디야").description("성공회대 앞 2층").address("구로구 어쩌구").maxCapacity(30).owner(user).build());
            List<SpaceDetail> spaceDetails = new ArrayList<>();
            for (int j = 0; j < 3; j++)
                spaceDetails.add(SpaceDetail.builder().space(space).name("테이블" + j).description("원형입니다").capacity(8).pricePerHour(1.0).build());
            spaceDetailRepository.saveAll(spaceDetails);
            space.addSpaceDetails(spaceDetails);
            spaceRepository.save(space);

            CreateReservationRequestDTO request = new CreateReservationRequestDTO(1L,
                LocalDateTime.parse("2023-09-10T10:36:25.400357"),
                LocalDateTime.parse("2023-09-10T10:36:25.400357"));

            //when & then
            assertThatThrownBy(() -> reservationService.createReservation(request,user,spaceDetails.get(0)))
                .isInstanceOf(PastTimeReservationTimeExeption.class);
        }

        @Test
        @DisplayName("예약 신청이 정상적으로 처리되어야만 한다.")
        void normal() {
            //given
            String providerRegistrationId = OAuth2Provider.GOOGLE.getRegistrationId();
            Provider provider = providerRepository.findByName(providerRegistrationId);
            UserRole userRole = userRoleRepository.findByRole("user");
            User fixture = UserFixture.createUser("name", "nickname", "email", provider, userRole);
            User user = userRepository.save(fixture);

            Space space = spaceRepository.save(Space.builder().name("이디야").description("성공회대 앞 2층").address("구로구 어쩌구").maxCapacity(30).owner(user).build());
            List<SpaceDetail> spaceDetails = new ArrayList<>();
            for (int j = 0; j < 3; j++)
                spaceDetails.add(SpaceDetail.builder().space(space).name("테이블" + j).description("원형입니다").capacity(8).pricePerHour(1.0).build());
            spaceDetailRepository.saveAll(spaceDetails);
            space.addSpaceDetails(spaceDetails);
            spaceRepository.save(space);

            SpaceDetail spaceDetail = spaceDetails.get(0);

            CreateReservationRequestDTO request = new CreateReservationRequestDTO(spaceDetail.getId(),
                LocalDateTime.parse("2024-11-23T19:36:25.400357"),
                LocalDateTime.parse("2024-11-23T20:36:25.400357"));

            assertThat(reservationService.createReservation(request, user, spaceDetail)).isInstanceOf(Reservation.class);
        }
    }

}
