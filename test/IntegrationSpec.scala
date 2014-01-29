import org.specs2.execute.Pending
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "TaskForm" should {

    "return only content with onlyContent=true" in {
      "by GET" in new WithBrowser {
        Pending("to research test with session.")

        /*
        browser.goTo("http://localhost:" + port + "/task/form?onlyContent=true")

        browser.pageSource must not contain("<html>")
        browser.pageSource must not contain("<head>")
        browser.pageSource must not contain("</head>")
        browser.pageSource must not contain("<body>")
        browser.pageSource must not contain("</body>")
        browser.pageSource must not contain("</html>")

        browser.pageSource must contain("<form>")
        browser.pageSource must contain("</form>")
        */
      }
    }
  }
}
