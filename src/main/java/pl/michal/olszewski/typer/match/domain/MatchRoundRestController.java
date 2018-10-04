package pl.michal.olszewski.typer.match.domain;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;
import pl.michal.olszewski.typer.match.dto.read.MatchRoundInfo;

@RestController
@RequestMapping("/api/match/round")
class MatchRoundRestController {

  private final MatchRoundFinder matchRoundFinder;
  private final MatchLeagueFinder matchLeagueFinder;
  private final MatchRoundSaver matchRoundSaver;

  public MatchRoundRestController(MatchRoundFinder matchRoundFinder, MatchLeagueFinder matchLeagueFinder, MatchRoundSaver matchRoundSaver) {
    this.matchRoundFinder = matchRoundFinder;
    this.matchLeagueFinder = matchLeagueFinder;
    this.matchRoundSaver = matchRoundSaver;
  }

  @GetMapping("/{id}")
  ResponseEntity<MatchRoundInfo> getMatchRoundInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(matchRoundFinder.findOneOrThrow(id).toMatchRoundInfo());
  }


  @PostMapping("")
  ResponseEntity<String> createNewMatchRound(@Valid @ModelAttribute("createNewMatch") CreateNewRound createNewRound) {
    MatchRound from = MatchRoundCreator.from(createNewRound, matchLeagueFinder);
    matchRoundSaver.save(from);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
