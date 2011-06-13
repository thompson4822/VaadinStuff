package com.testPlan

import org.scalaVaadin._
import xml.Elem

class TestPlanApplication extends VaadinApplication {

  val projectsTooltip: Elem =
    <span>
      <h2><img src="../VAADIN/themes/sampler/icons/comment_yellow.gif"/>A richtext tooltip</h2>
      <ul>
        <li>Use rich formatting with XHTML</li>
        <li>Include images from themes</li>
        <li>etc.</li>
      </ul>
    </span>

  val projectsButton = {
    val result = new Button("Projects", _ => ())
    result.setDescription(projectsTooltip.toString)
    result
  }

  val mainMenu = {
    val result = new HorizontalLayout
    val label = new HtmlLabel(<b>Test Plan</b>)
    result.setDescription("Test Plan")
    result.addComponents(
      label,
      projectsButton,
      new Button("Log In", _ => ()))
    result.setSpacing(true)

    // Individual alignment
    result align label bottomLeft

    // Group alignment
    result.components.foreach(c => result align c middleCenter)

    result
  }

  val contentView = new HorizontalLayout

  val layout = {
    val result = new VerticalLayout
    result.addComponents(mainMenu, contentView)
    result
  }

  override def init() {
    val mainWindow = new Window("Test Plan")
    mainWindow.addComponent(layout)
    setMainWindow(mainWindow)
  }
}