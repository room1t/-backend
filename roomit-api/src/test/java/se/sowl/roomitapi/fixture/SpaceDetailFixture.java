package se.sowl.roomitapi.fixture;

import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;

public class SpaceDetailFixture {
    public static SpaceDetail createSpaceDetail(Space space, String name, String description, Integer capacity, Double pricePerHour) {
        return SpaceDetail.builder()
                .space(space)
                .name(name)
                .description(description)
                .capacity(capacity)
                .pricePerHour(pricePerHour)
                .build();
    }
}
