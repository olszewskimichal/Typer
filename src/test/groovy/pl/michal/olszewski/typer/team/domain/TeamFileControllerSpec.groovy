package pl.michal.olszewski.typer.team.domain

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.mock.web.MockMultipartFile
import pl.michal.olszewski.typer.BaseWebMvcSpec
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [TeamFileController])
class TeamFileControllerSpec extends BaseWebMvcSpec {

    def 'should save uploaded file with teams'() {
        given:
        def multipartFile = new MockMultipartFile("file", "teams.xlsx",
                "text/plain", "Spring Framework".getBytes())
        when:
        def resultActions = doRequest(multipart("/team/uploadFile").file(multipartFile))
        then:
        resultActions.andExpect(status().isOk())
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        TeamFileAdapter registrationService() {
            return detachedMockFactory.Stub(TeamFileAdapter)
        }
    }
}
