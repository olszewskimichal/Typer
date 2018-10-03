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
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;
import pl.michal.olszewski.typer.match.dto.read.MatchInfo;

@RestController
@RequestMapping("/api/match")
public class MatchRestController {

  private final MatchRoundFinder matchRoundFinder;
  private final MatchFinder matchFinder;
  private final MatchSaver matchSaver;

  public MatchRestController(MatchRoundFinder matchRoundFinder, MatchFinder matchFinder, MatchSaver matchSaver) {
    this.matchRoundFinder = matchRoundFinder;
    this.matchFinder = matchFinder;
    this.matchSaver = matchSaver;
  }

  @GetMapping("/{id}")
  ResponseEntity<MatchInfo> getMatchInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(matchFinder.findOneOrThrow(id).toMatchInfo());
  }


  @PostMapping("")
  ResponseEntity<String> createNewMatch(@Valid @ModelAttribute("createNewMatch") CreateNewMatch createNewMatch) {
    Match from = MatchCreator.from(createNewMatch, matchRoundFinder);
    matchSaver.save(from);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
