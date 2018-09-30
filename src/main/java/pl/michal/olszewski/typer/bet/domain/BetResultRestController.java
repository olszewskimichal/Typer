package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

@RestController
@RequestMapping("/api/bet/result")
class BetResultRestController {

  private final BetResultService betResultService;

  BetResultRestController(BetResultService betResultService) {
    this.betResultService = betResultService;
  }

  @GetMapping("/user/{id}")
  ResponseEntity<List<BetResult>> getUserResults(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betResultService.getUserResults(id));
  }


  @GetMapping("/match/{id}")
  ResponseEntity<List<BetResult>> getMatchResults(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betResultService.getBetResultsForMatch(id));
  }


  @GetMapping("/round/{id}")
  ResponseEntity<List<BetResult>> getRoundResults(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betResultService.getBetResultsForRound(id));
  }

  @GetMapping("/{id}")
  ResponseEntity<BetResult> getResultByBetId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betResultService.getResultByBetId(id));
  }

}
