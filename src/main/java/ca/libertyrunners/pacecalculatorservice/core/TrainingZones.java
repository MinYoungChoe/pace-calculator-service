package ca.libertyrunners.pacecalculatorservice.core;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingZones {
    private EasyPace easy;
    private Map<String, String> marathon;
    private Map<String, String> threshold;
    private Map<String, String> interval;
    private Map<String, String> repetition;
    private Map<String, String> fastReps;



}
