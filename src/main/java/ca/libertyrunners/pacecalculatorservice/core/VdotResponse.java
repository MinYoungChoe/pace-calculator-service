package ca.libertyrunners.pacecalculatorservice.core;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VdotResponse {

    private Double vdot;
    private List<RacePace> racePaces;
    private TrainingZones trainingZones;
    private List<EquivalentRace> equivalentRaces;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

}
