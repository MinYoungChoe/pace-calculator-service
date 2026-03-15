package ca.libertyrunners.pacecalculatorservice.core;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingZones {
    private EasyPace easy;
    private String marathon;
    private String threshold;
    private String interval;
    private String repetition;
}

