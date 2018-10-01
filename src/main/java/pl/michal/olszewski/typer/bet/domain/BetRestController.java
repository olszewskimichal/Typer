package pl.michal.olszewski.typer.bet.domain;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;
import pl.michal.olszewski.typer.bet.dto.read.BetInfo;

@RestController
@RequestMapping("/api/bet")
public class BetRestController {

  private final BetFinder betFinder;
  private final BetSaver betSaver;

  public BetRestController(BetFinder betFinder, BetSaver betSaver) {
    this.betFinder = betFinder;
    this.betSaver = betSaver;
  }

  @GetMapping("/{id}")
  ResponseEntity<BetInfo> getBetInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(betFinder.findOneOrThrow(id).toBetInfo());
  }


  @PostMapping("")
  ResponseEntity<String> createNewBet(@Valid @ModelAttribute("createNewBet") CreateNewBet createNewBet/*, Errors errors*/) {
    Bet from = BetCreator.from(createNewBet);
    betSaver.save(from);
    return ResponseEntity.ok("OK");
  }
}
