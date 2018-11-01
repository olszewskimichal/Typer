package pl.michal.olszewski.typer.users.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserValidationApiSpec extends UserMvcSpec {

    @Autowired
    UserSaver saver

    @Unroll
    def 'should throw exception when request is incorrect'() {
        given:
        saver.save(User.builder().username("name").email("existingEmail").build())
        Map request = [
                email   : email,
                username: username
        ]
        when:
        def results = doRequest(post("/api/user").contentType(APPLICATION_JSON).content(toJson(request)))
        then:
        results.andExpect(status().is(result))

        where:
        email           | username | result
        null            | 'user'   | HttpStatus.UNPROCESSABLE_ENTITY.value()
        'email'         | null     | HttpStatus.UNPROCESSABLE_ENTITY.value()
        'existingEmail' | 'name'   | HttpStatus.CONFLICT.value()
    }
}
