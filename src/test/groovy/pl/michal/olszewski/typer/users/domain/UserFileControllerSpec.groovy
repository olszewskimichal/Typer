package pl.michal.olszewski.typer.users.domain

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.mock.web.MockMultipartFile
import pl.michal.olszewski.typer.BaseWebMvcSpec
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserFileController])
class UserFileControllerSpec extends BaseWebMvcSpec {

    def 'should save uploaded file with users'() {
        given:
        MockMultipartFile multipartFile = new MockMultipartFile("file", "users.xlsx",
                "text/plain", "Spring Framework".getBytes())
        when:
        def results = doRequest(multipart("/users/uploadFile").file(multipartFile))
        then:
        results.andExpect(status().isOk())
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        UserFileAdapter registrationService() {
            return detachedMockFactory.Stub(UserFileAdapter)
        }
    }

}
