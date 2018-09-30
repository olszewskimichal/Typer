package pl.michal.olszewski.typer.bet.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.bet.dto.read.BetInfo;

@RestController
@RequestMapping("/api/bet")
public class BetRestController {
    private final BetFinder betFinder;

    public BetRestController(BetFinder betFinder) {
        this.betFinder = betFinder;
    }

    @GetMapping("/{id}")
    ResponseEntity<BetInfo> getBetInfoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(betFinder.findOneOrThrow(id).toBetInfo());
    }
}
