package pl.michal.olszewski.typer.bet.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

@RestController
@RequestMapping("/api/bet/statistics")
class BetStatisticsRestController {

  private final BetStatisticsService betStatisticsService;

  BetStatisticsRestController(BetStatisticsService betStatisticsService) {
    this.betStatisticsService = betStatisticsService;
  }

  @GetMapping("league/{leagueId}/user/{userId}")
  ResponseEntity<BetLeagueUserStatistics> getUserLeagueStats(@PathVariable("leagueId") Long leagueId, @PathVariable("userId") Long userId) {
    return ResponseEntity.ok(betStatisticsService.getStatisticsForUserAndLeague(userId, leagueId));
  }


  @GetMapping("round/{roundId}/user/{userId}")
  ResponseEntity<BetRoundUserStatistics> getUserRoundStats(@PathVariable("roundId") Long roundId, @PathVariable("userId") Long userId) {
    return ResponseEntity.ok(betStatisticsService.getStatisticsForUserAndRound(userId, roundId));
  }

}
