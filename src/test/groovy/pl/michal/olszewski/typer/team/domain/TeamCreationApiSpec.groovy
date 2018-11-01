package pl.michal.olszewski.typer.team.domain

import org.springframework.beans.factory.annotation.Autowired

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TeamCreationApiSpec extends TeamMvcSpec {

    @Autowired
    TeamSaver saver

    def setup() {
        saver.deleteAll()
    }

    def 'Should create new team'() {
        given:
        Map request = [
                name: 'nowaNazwa'
        ]
        when:
        def results = doRequest(post("/api/team").contentType(APPLICATION_JSON).content(toJson(request)))
        then:
        results.andExpect(status().isCreated())
    }
}
