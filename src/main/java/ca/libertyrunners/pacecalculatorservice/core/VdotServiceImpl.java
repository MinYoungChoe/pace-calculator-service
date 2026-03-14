package ca.libertyrunners.pacecalculatorservice.core;
import java.util.List;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;

@AllArgsConstructor
@Service
@Slf4j
public class VdotServiceImpl implements VdotService {

    private final VdotValidator validator;

    public VdotResponse calculateVdot(VdotRequest vdotRequest) {
        Double distance = vdotRequest.getDistance() == null ? 0D : vdotRequest.getDistance();
        Duration time = setupTime(vdotRequest);
        Duration pace = setupPace(vdotRequest);
        val error = validator.validate(distance, time, pace);

        if (pace.isZero()) {
            // calculate pace from distance + time
            long paceInSeconds = Math.round(time.toSeconds() / distance);
            pace = Duration.ofSeconds(paceInSeconds);
        }

        if (error != null) {
            return VdotResponse.builder().error(error).build();
        }
        double timeInMin = time.toSeconds() / 60.0;
        double distanceInMeters = distance * 1000;
        double vdot = computeVdotScore(distanceInMeters, timeInMin);
        List<RacePace> racePaces = buildRacePaces(pace, time);

        return VdotResponse.builder()
                .vdot(vdot)
                .racePaces(racePaces)
                .build();



    }

    private double computeVdotScore(double distanceInMetres, double timeInMinutes) {
        double velocity = distanceInMetres / timeInMinutes;


        double numerator = -4.60
                + 0.182258 * velocity
                + 0.000104 * Math.pow(velocity, 2);

        double denominator = 0.8
                + 0.1894393 * Math.exp(-0.012778 * timeInMinutes)
                + 0.2989558 * Math.exp(-0.1932605 * timeInMinutes);


        return numerator / denominator;
    }
    private Duration setupTime(VdotRequest vdotRequest) {
        Duration totalTime = Duration.ZERO;

        if(vdotRequest.getHour() != null){
            totalTime = totalTime.plusHours(vdotRequest.getHour());
        }

        if(vdotRequest.getMinute() != null){
            totalTime = totalTime.plusMinutes(vdotRequest.getMinute());
        }

        if(vdotRequest.getSecond() != null){
            totalTime = totalTime.plusSeconds(vdotRequest.getSecond());
        }

        return totalTime;
    }

    private Duration setupPace(VdotRequest vdotRequest) {
        Duration totalPace = Duration.ZERO;

        if(vdotRequest.getPaceMinute() != null) {
            totalPace = totalPace.plusMinutes(vdotRequest.getPaceMinute());
        }

        if(vdotRequest.getPaceSecond() != null) {
            totalPace = totalPace.plusSeconds(vdotRequest.getPaceSecond());
        }

        return totalPace;
    }



    private String formatTime(double timeInMinutes) {
        long totalSeconds = Math.round(timeInMinutes * 60);
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;


        if (hours > 0) {
            return hours + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        }
        return minutes + ":" + String.format("%02d", seconds);
    }

    private List<RacePace> buildRacePaces(Duration pace, Duration time) {
        long paceSeconds = pace.toSeconds();

        List<RacePace> racePaces = new ArrayList<>();

        racePaces.add(RacePace.builder()
                .distance("Marathon")
                .pace(formatTime(time.toSeconds() / 60.0))
                .build());

        racePaces.add(RacePace.builder()
                .distance("1Mi")
                .pace(formatTime(paceSeconds * 1.609 / 60))
                .build());

        racePaces.add(RacePace.builder()
                .distance("1K")
                .pace(formatTime(paceSeconds * 1.0 / 60))
                .build());

        racePaces.add(RacePace.builder()
                .distance("800M")
                .pace(formatTime(paceSeconds * 0.8 / 60))
                .build());

        racePaces.add(RacePace.builder()
                .distance("400M")
                .pace(formatTime(paceSeconds * 0.4 / 60))
                .build());

        return racePaces;
    }

}
