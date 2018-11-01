package pl.michal.olszewski.typer.team.domain;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam;
import pl.michal.olszewski.typer.team.dto.read.TeamInfo;

@RestController
@RequestMapping("/api/team")
class TeamRestController {

  private final TeamFinder teamFinder;
  private final TeamSaver teamSaver;

  public TeamRestController(TeamFinder teamFinder, TeamSaver teamSaver) {
    this.teamFinder = teamFinder;
    this.teamSaver = teamSaver;
  }

  @GetMapping("/{id}")
  ResponseEntity<TeamInfo> getTeamInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(teamFinder.findOneOrThrow(id).toTeamInfo());
  }


  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  ResponseEntity<String> createNewTeam(@RequestBody @Valid CreateNewTeam createNewTeam) {
    Team from = TeamCreator.from(createNewTeam, teamFinder);
    teamSaver.save(from);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
