package se.sowl.roomitapi.fixture;

import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.user.domain.User;

public class SpaceFixture {
    public static Space createSpace(String name, String description, String address, Integer maxCapacity, User owner) {
        return Space.builder()
                .name(name)
                .description(description)
                .address(address)
                .maxCapacity(maxCapacity)
                .owner(owner)
                .build();
    }

}
