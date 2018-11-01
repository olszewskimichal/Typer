package pl.michal.olszewski.typer.team.domain

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pl.michal.olszewski.typer.BaseWebMvcSpec

@WebMvcTest(controllers = [TeamRestController])
abstract class TeamMvcSpec extends BaseWebMvcSpec {

    @TestConfiguration
    static class StubConfig {

        @Bean
        TeamFinder registrationFinder() {
            return new InMemoryTeamFinder()
        }

        @Bean
        TeamSaver registrationSaver() {
            return new InMemoryTeamSaver()
        }

    }
}
