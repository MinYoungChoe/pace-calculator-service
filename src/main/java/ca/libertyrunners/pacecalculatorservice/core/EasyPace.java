package ca.libertyrunners.pacecalculatorservice.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EasyPace {
    private String min;
    private String max;
}
