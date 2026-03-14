package ca.libertyrunners.pacecalculatorservice.api;

import ca.libertyrunners.pacecalculatorservice.core.VdotRequest;
import ca.libertyrunners.pacecalculatorservice.core.VdotResponse;
import ca.libertyrunners.pacecalculatorservice.core.VdotService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class VdotController {

    private final VdotService vdotService;

    @GetMapping(value = "/calculate-vdot")
    public ResponseEntity<VdotResponse> calculateVdot(
            @RequestParam(required = false) Double distance,
            @RequestParam(required = false) Integer hour,
            @RequestParam(required = false) Integer minute,
            @RequestParam(required = false) Integer second,
            @RequestParam(required = false) Integer paceMinute,
            @RequestParam(required = false) Integer paceSecond) {

        VdotRequest request = VdotRequest.builder()
                .distance(distance)
                .hour(hour).minute(minute).second(second)
                .paceMinute(paceMinute).paceSecond(paceSecond)
                .build();

        val result = vdotService.calculateVdot(request);
        if (result.getError() != null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(result);
    }
}
