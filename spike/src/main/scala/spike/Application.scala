package spike

import java.util.Date

import org.scalaVaadin._

class SpikeApplication extends VaadinApplication {
  override def init() {
    val mainWindow = new Window("Spike Application")
    val label = new Label("Hello Spike Application User!")
    mainWindow.addComponent(label)
    mainWindow.addComponent(
      new Button("What is the time?", _ => mainWindow.showNotification("The time is " + new Date())))
    setMainWindow(mainWindow)
  }

}