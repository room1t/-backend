package se.sowl.roomitdomain.space.SpaceDetailDto;

import lombok.Builder;
import lombok.Data;
import se.sowl.roomitdomain.space.domain.Space;

@Data
public class SpaceDetailResponseDto {

    private Space space;
    private String name;
    private String description;
    private Integer capacity;
    private Double pricePerHour;

    @Builder
    public SpaceDetailResponseDto(Space space, String name, String description, Integer capacity, Double price) {
        this.space = space;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerHour = price;
    }

}
