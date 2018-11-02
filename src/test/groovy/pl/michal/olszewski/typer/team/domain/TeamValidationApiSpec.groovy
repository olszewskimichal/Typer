package pl.michal.olszewski.typer.team.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TeamValidationApiSpec extends TeamMvcSpec {

    @Autowired
    TeamSaver saver

    @Unroll
    def 'should throw exception when request is incorrect'() {
        given:
        saver.save(Team.builder().name("name").build())
        Map request = [
                name: name,
        ]
        when:
        def results = doRequest(post("/api/team").contentType(APPLICATION_JSON).content(toJson(request)))
        then:
        results.andExpect(status().is(result))

        where:
        name   | result
        null   | HttpStatus.UNPROCESSABLE_ENTITY.value()
        'name' | HttpStatus.CONFLICT.value()
    }

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
