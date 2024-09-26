package se.sowl.roomitapi.reservation.controller;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import se.sowl.roomitapi.fixture.UserFixture;
import se.sowl.roomitapi.reservation.dto.CreateReservationRequestDTO;
import se.sowl.roomitapi.reservation.exception.AlreadyReservedTimeException;
import se.sowl.roomitapi.reservation.service.ReservationService;
import se.sowl.roomitdomain.reservation.domain.Reservation;
import se.sowl.roomitdomain.reservation.domain.ReservationStatus;
import se.sowl.roomitdomain.reservation.repository.ReservationRepository;
import se.sowl.roomitdomain.reservation.repository.ReservationStatusRepository;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;
import se.sowl.roomitdomain.space.repository.SpaceDetailRepository;
import se.sowl.roomitdomain.space.repository.SpaceRepository;
import se.sowl.roomitdomain.user.domain.User;
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
public class ReservationControllerTest {

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

    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    private User user;
    private Space space;
    private List<SpaceDetail> spaceDetails;
    private Reservation existingReservation;

    @BeforeEach
    void setUp() {
        user = UserFixture.createUser("name", "nickname", "email", providerRepository.findByName("google"), userRoleRepository.findByRole("user"));
        user = userRepository.save(user);

        space = Space.builder().name("이디야").description("성공회대 앞 2층").address("구로구 어쩌구").maxCapacity(30).owner(user).build();
        space = spaceRepository.save(space);

        spaceDetails = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            spaceDetails.add(SpaceDetail.builder().space(space).name("테이블" + j).description("원형입니다").capacity(8).pricePerHour(1.0).build());
        }
        spaceDetailRepository.saveAll(spaceDetails);
        space.addSpaceDetails(spaceDetails);
        spaceRepository.save(space);
        ReservationStatus status = new ReservationStatus("예약 중");
        reservationStatusRepository.save(status);

        existingReservation = Reservation.builder()
            .user(user)
            .status(reservationStatusRepository.findByName("예약 중"))
            .spaceDetail(spaceDetails.get(0))
            .startTime(LocalDateTime.parse("2024-10-16T18:36:25.400357"))
            .endTime(LocalDateTime.parse("2024-10-16T19:36:25.400357"))
            .build();

        reservationRepository.save(existingReservation);
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        spaceDetailRepository.deleteAll();
        spaceRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("Reservation tests")
    class ReservationTests {
        @Test
        @DisplayName("Should throw AlreadyReservedTimeException when trying to reserve an already reserved time slot")
        void createReservationWithOverlappingTime() {
            CreateReservationRequestDTO request = new CreateReservationRequestDTO(spaceDetails.get(0).getId(),
                LocalDateTime.parse("2024-10-16T18:36:25.400357"),
                LocalDateTime.parse("2024-10-16T19:36:25.400357"));

            assertThat(reservationRepository.findById(existingReservation.getId())).isPresent();
            assertThatThrownBy(() -> reservationService.createReservation(request, user, spaceDetails.get(0)))
                .isInstanceOf(AlreadyReservedTimeException.class);
        }
    }
}
