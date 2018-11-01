package pl.michal.olszewski.typer.users.domain


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import pl.michal.olszewski.typer.BaseWebMvcSpec

@WebMvcTest(controllers = [UserRestController])
abstract class UserMvcSpec extends BaseWebMvcSpec {

    @TestConfiguration
    static class StubConfig {

        @Bean
        UserFinder registrationFinder() {
            return new InMemoryUserFinder()
        }

        @Bean
        UserSaver registrationSaver() {
            return new InMemoryUserSaver()
        }

    }
}
