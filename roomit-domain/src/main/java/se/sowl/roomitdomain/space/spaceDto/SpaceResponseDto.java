package se.sowl.roomitdomain.space.spaceDto;

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
}
