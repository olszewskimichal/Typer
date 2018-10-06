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
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;
import pl.michal.olszewski.typer.match.dto.read.MatchLeagueInfo;

@RestController
@RequestMapping("/api/match/league")
class MatchLeagueRestController {

  private final MatchLeagueFinder matchLeagueFinder;
  private final MatchLeagueSaver matchLeagueSaver;

  public MatchLeagueRestController(MatchLeagueFinder matchLeagueFinder, MatchLeagueSaver matchLeagueSaver) {
    this.matchLeagueFinder = matchLeagueFinder;
    this.matchLeagueSaver = matchLeagueSaver;
  }

  @GetMapping("/{id}")
  ResponseEntity<MatchLeagueInfo> getMatchLeagueInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(matchLeagueFinder.findOneOrThrow(id).toMatchLeagueInfo());
  }


  @PostMapping("")
  ResponseEntity<String> createNewMatchLeague(@Valid @ModelAttribute("createNewLeague") CreateNewLeague createNewLeague) {
    MatchLeague from = MatchLeagueCreator.from(createNewLeague);
    matchLeagueSaver.save(from);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
