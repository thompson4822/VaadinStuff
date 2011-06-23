package com.example.addressBook.ui

import org.scalaVaadin._
import com.example.addressBook.data.{Person, PersonContainer}

class PersonForm(dataSource: PersonContainer) extends Form {
  private val save = new Button("Save", onSave)
  private val cancel = new Button("Cancel", onCancel)
  private val edit = new Button("Edit", _ => setReadOnly(false))

  private var newContactMode = false
  private var newPerson: Person = _


  private def onSave(event: ButtonClickEvent) {
    if(isValid) {
        commit()
        if(newContactMode) {
          val addedItem = dataSource.addItem(newPerson)
          setItemDataSource(addedItem)
          newContactMode = false
        }
        setReadOnly(true)
    }
  }

  private def onCancel(event: ButtonClickEvent) {
    if(newContactMode) {
      newContactMode = false
      setItemDataSource(null)
    }
    else
      discard()
    setReadOnly(true)
  }

  def addContact() {
    // Create a temporary item for the form
    newPerson = new Person()
    setItemDataSource(new BeanItem(newPerson))
    newContactMode = true
    setReadOnly(false)
  }

  override def setItemDataSource(newDataSource: Item) {
    import scala.collection.JavaConverters._
    newContactMode = false
    newDataSource match {
      case myDataSource if(myDataSource != null) =>
        super.setItemDataSource(myDataSource, PersonList.columnDefinitions.map(col => col.field).asJavaCollection)
        setReadOnly(true)
      case _ =>
        super.setItemDataSource(null)
    }
    getFooter setVisible (newDataSource != null)
  }

  setWriteThrough(false)
  val footer = new HorizontalLayout
  footer.setSpacing(true)
  footer.addComponents(save, cancel, edit)
  footer.setVisible(false)
  setFooter(footer)



}