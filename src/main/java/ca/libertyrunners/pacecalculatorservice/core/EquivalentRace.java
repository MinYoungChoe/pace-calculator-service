package ca.libertyrunners.pacecalculatorservice.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquivalentRace {
    private String race;
    private String time;
    private String pacePerKm;
}
