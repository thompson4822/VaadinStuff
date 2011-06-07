package spike

import org.scalaVaadin._
/*
class SWindowCloseListener(action: Window#CloseEvent => Unit) extends Window.CloseListener {
  def windowClose(event: Window#CloseEvent) { action(event) }
}
*/

class WindowOpener(label: String, main: Window) extends CustomComponent {
  private val openButton: Button = new Button("Open Window", _ => createSubWindow())
  private val explanation: Label = new Label("Explanation")
  private val layout = new VerticalLayout
  layout.addComponent(openButton)
  layout.addComponent(explanation)
  setCompositionRoot(layout)

  private var subWindow: Window = _

  private def createSubWindow() {
    subWindow = new Window("My Dialog")
    subWindow.setPositionX(200)
    subWindow.setPositionY(100)
    subWindow.addComponent(new Label("A text label in the window."))
    subWindow.addComponent(new Button("Close", _ => closeSubWindow()))
    subWindow.addListener(new WindowCloseListener(_ => onSubWindowClose()))
    mainWindow.addWindow(subWindow)
    openButton.setEnabled(false)
    explanation.setValue("Window Opened")
  }

  var mainWindow: Window = _
  var myWindow: Window = _
  var closeButton: Button = _
  mainWindow = main

  def closeSubWindow() {
    mainWindow.removeWindow(subWindow)
    openButton.setEnabled(true)
    explanation.setValue("Closed with the button")
  }

  def onSubWindowClose() {
    openButton.setEnabled(true)
    explanation.setValue("Closed with window controls")
  }
}

class SpikeApplication2 extends VaadinApplication {
  override def init() {
    val main = new Window("The Main Window")
    setMainWindow(main)
    main.addComponent(new WindowOpener("Window Opener", main))
  }

}