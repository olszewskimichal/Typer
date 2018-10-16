package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

@RestController
@RequestMapping("/api/bet/top")
class BetTopRestController {

  private final BetStatisticsService betStatisticsService;

  BetTopRestController(BetStatisticsService betStatisticsService) {
    this.betStatisticsService = betStatisticsService;
  }

  @GetMapping("league/{leagueId}")
  ResponseEntity<List<BetLeagueUserStatistics>> getLeagueTOP(@PathVariable("leagueId") Long leagueId, @RequestParam(value = "pageSize", required = true) Long pageSize) {
    return ResponseEntity.ok(betStatisticsService.getLeagueTOP(leagueId, pageSize));
  }


  @GetMapping("round/{roundId}")
  ResponseEntity<List<BetRoundUserStatistics>> getRoundTOP(@PathVariable("roundId") Long roundId, @RequestParam(value = "pageSize", required = true) Long pageSize) {
    return ResponseEntity.ok(betStatisticsService.getRoundTOP(roundId, pageSize));
  }

}
