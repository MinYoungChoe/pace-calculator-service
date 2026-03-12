package ca.libertyrunners.pacecalculatorservice.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RacePace {
    private String distance;
    private String pace;
}