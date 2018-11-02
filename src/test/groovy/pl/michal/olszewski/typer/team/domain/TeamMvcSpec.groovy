package pl.michal.olszewski.typer.team.domain

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import pl.michal.olszewski.typer.BaseWebMvcSpec

@WebMvcTest(controllers = [TeamRestController])
abstract class TeamMvcSpec extends BaseWebMvcSpec {
    
}
