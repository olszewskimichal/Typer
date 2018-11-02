package pl.michal.olszewski.typer.team.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class TeamInfoReturningApiSpec extends TeamMvcSpec {

    @Autowired
    TeamSaver saver

    def 'should return status not found when team by id not exists'() {
        when:
        def results = doRequest(get("/api/team/{id}", 254))
        then:
        results.andExpect(status().isNotFound())
    }

    def 'should return team info when team by id exists in DB'() {
        given:
        def team = Team.builder().id(3L).name("name").build()
        saver.save(team)
        when:
        def results = doRequest(get("/api/team/{id}", team.getId()))
        then:
        results.andExpect(status().isOk())
        and:
        results.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        and:
        results.andExpect(jsonPath('$.id').value(team.getId().intValue()))
        results.andExpect jsonPath('$.name').value(team.name)
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
