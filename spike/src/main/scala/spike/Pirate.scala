package spike


import org.scalaVaadin._
import reflect.BeanProperty

class Pirate(@BeanProperty var name: String = "", @BeanProperty var weight: Int = 0)

class SpikeApplication3 extends VaadinApplication {
  override def init {
    val mainWindow = new Window("And now ... a form")
    val form = new Form()
    form.setCaption("This be the caption.  Yes it be, it do!")
    form.setDescription("What we 'ave 'ere is a tool fur the displayin' o' pirates")
    form.setItemDataSource(new BeanItem(new Pirate("Bob")))
    form.setValidationVisible(true)
    mainWindow.addComponent(form)
    setMainWindow(mainWindow)
  }
}