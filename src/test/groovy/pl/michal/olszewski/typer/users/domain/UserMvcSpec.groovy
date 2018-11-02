package pl.michal.olszewski.typer.users.domain

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import pl.michal.olszewski.typer.BaseWebMvcSpec

@WebMvcTest(controllers = [UserRestController])
abstract class UserMvcSpec extends BaseWebMvcSpec {


}
