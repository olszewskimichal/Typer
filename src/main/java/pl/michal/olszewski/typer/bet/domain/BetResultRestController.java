package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.bet.dto.read.BetResult;

@RestController
class BetResultRestController {

  private final BetResultService betResultService;

  public BetResultRestController(BetResultService betResultService) {
    this.betResultService = betResultService;
  }

  @GetMapping("/api/bet/user/{id}")
  ResponseEntity<List<BetResult>> getUserResults(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betResultService.getUserResults(id));
  }


  @GetMapping("/api/bet/{id}")
  ResponseEntity<BetResult> getResultByBetId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betResultService.getResultByBetId(id));
  }

}
