package se.sowl.roomitdomain.space.SpaceDetailDto;

import lombok.Builder;
import lombok.Data;
import se.sowl.roomitdomain.space.domain.Space;
import se.sowl.roomitdomain.space.domain.SpaceDetail;

@Data
public class SpaceDetailResponseDto {

    private String name;
    private String description;
    private Integer capacity;
    private Double pricePerHour;

    @Builder
    public SpaceDetailResponseDto(String name, String description, Integer capacity, Double pricePerHour) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
    }

    public static SpaceDetailResponseDto toDTO(SpaceDetail spaceDetail){
        return SpaceDetailResponseDto.builder()
                .name(spaceDetail.getName())
                .description(spaceDetail.getDescription())
                .capacity(spaceDetail.getCapacity())
                .pricePerHour(spaceDetail.getPricePerHour())
                .build();
    }
}
