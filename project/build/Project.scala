
import sbt._

class Project(info: ProjectInfo) extends ParentProject(info) {
/*
  val vaadinVersion = "6.6.0"

  val jettyDep = "org.mortbay.jetty" % "jetty" % "6.1.18" % "test->default"
  val vaadinDep = "com.vaadin" % "vaadin" % vaadinVersion
*/

  lazy val scalaVaadin = project("ScalaVaadin", "ScalaVaadin", new ScalaVaadinProject(_))
  lazy val spike = project("spike", "spike", new DefaultWebProject(_), scalaVaadin)
  lazy val bookProject = project("BookProject", "BookProject", new DefaultWebProject(_), scalaVaadin)
  lazy val addressBookProject = project("AddressBook", "AddressBook", new DefaultWebProject(_), scalaVaadin)
  lazy val testPlan = project("TestPlan", "TestPlan", new DefaultWebProject(_), scalaVaadin)
  
  class ScalaVaadinProject(info: ProjectInfo) extends DefaultProject(info)
  {
    val vaadinVersion = "6.6.2"

    val jettyDep = "org.mortbay.jetty" % "jetty" % "6.1.18" % "test->default"
    val vaadinDep = "com.vaadin" % "vaadin" % vaadinVersion
  }

}