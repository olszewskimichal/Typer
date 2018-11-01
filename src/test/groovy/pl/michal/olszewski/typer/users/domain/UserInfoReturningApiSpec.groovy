package pl.michal.olszewski.typer.users.domain

import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserInfoReturningApiSpec extends UserMvcSpec {

    @Autowired
    UserSaver saver

    def 'should return status not found when user by id not exists'() {
        when:
        def results = doRequest(get("/api/user/{id}", 254))
        then:
        results.andExpect(status().isNotFound())
    }

    def 'should return user info when user by id exists in DB'() {
        given:
        def user = User.builder().id(3L).email("email").username("name").build()
        saver.save(user)
        when:
        def results = doRequest(get("/api/user/{id}", user.getId()))
        then:
        results.andExpect(status().isOk())
        and:
        results.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        and:
        results.andExpect(jsonPath('$.id').value(user.getId().intValue()))
        results.andExpect(jsonPath('$.email').value(user.getEmail()))
        results.andExpect(jsonPath('$.username').value(user.username))
    }
}
