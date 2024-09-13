package se.sowl.roomitdomain.space.spaceDto;

import lombok.Builder;
import lombok.Data;
import se.sowl.roomitdomain.space.domain.SpaceDetail;

import java.util.List;

@Data
public class SpaceResponseDto {

    private String name;
    private String description;
    private String address;
    private Integer maxCapacity;
    private List<SpaceDetail> spaceDetails;

    // Page<SpaceDTO>

    @Builder
    public SpaceResponseDto(String name, String description, String address, Integer maxCapacity, List<SpaceDetail> spaceDetails) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.spaceDetails = spaceDetails;
    }
}
