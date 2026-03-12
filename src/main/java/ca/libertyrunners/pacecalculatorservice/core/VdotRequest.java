package ca.libertyrunners.pacecalculatorservice.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VdotRequest {
    private Double distance;

    private Integer hour;

    private Integer minute;

    private Integer second;

    private Integer paceMinute;

    private Integer paceSecond;
}
