package com.testPlan

import model.{Project, FakeProjects}
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
    result.setDescription(projectsTooltip.toString())
    result
  }

  val exampleTree = {
    val container = new HierarchicalContainer
    container.addContainerProperty("icon", classOf[Resource], null)
    container.addContainerProperty("caption", classOf[String], null)
    (1 to 10).map {
      x =>
      val item = container.addItem()
      container.getContainerProperty(item, "icon").setValue(new ThemeResource("common/icons/error.png"))
      container.getContainerProperty(item, "caption").setValue("Item " + x)
    }
    val tree = new Tree
    tree.setContainerDataSource(container)
    tree.setItemIconPropertyId("icon")
    tree.setItemCaptionPropertyId("caption")
    //tree.setItemCaptionMode()
    tree
  }

  def handleMenuAction(menuItem: MenuItem) {
    println("You just clicked on " + menuItem.getText + " in the menu.")
  }

  val mainMenu = {
    val menu = new MenuBar
    val result = new VerticalLayout
    result.setDescription("Test Plan")
    result.addComponents(
      menu,
      exampleTree
/*
      label,
      projectsButton,
      new Button("Log In", _ => ()))
*/
    )
    val beverages = menu.add("Beverages")
    val hot = beverages.add("Hot")
    hot.add("Tea", handleMenuAction)
    hot.add("Coffee", handleMenuAction)
    val cold = beverages.addItem("Cold", null, null)
    cold.addItem("Milk", null, null)

    val snacks = menu.addItem("Snacks", null, null)
    snacks.addItem("Weisswurst", null, null)
    snacks.addItem("Salami", null, null)

    val services = menu.addItem("Services", null, null)
    services.addItem("Car Service", null, null)


    result.setSpacing(true)

    // Individual alignment
    //result align label bottomLeft

    // Group alignment
    //result.components.foreach(c => result align c middleCenter)

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
    mainWindow.addComponent(new NavigationTree)

    setMainWindow(mainWindow)

/*
    val projects: Seq[Project] = FakeProjects()
    projects.foreach{x => println(); println(x)}
*/
  }
}