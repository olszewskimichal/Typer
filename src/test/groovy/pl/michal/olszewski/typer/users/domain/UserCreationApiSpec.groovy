package pl.michal.olszewski.typer.users.domain


import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserCreationApiSpec extends UserMvcSpec {

    def 'Should create new user'() {
        given:
        Map request = [
                email   : 'email1234',
                username: 'user'
        ]
        when:
        def results = doRequest(post("/api/user").contentType(APPLICATION_JSON).content(toJson(request)))
        then:
        results.andExpect(status().isCreated())
    }
}
