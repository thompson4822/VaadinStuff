package com.example.addressBook.ui

import org.scalaVaadin._
import com.example.addressBook.data.PersonContainer

class PersonForm extends Form {
  private val save = new Button("Save", onSave)
  private val cancel = new Button("Cancel", onCancel)
  private val edit = new Button("Edit", _ => setReadOnly(false))

  private def onSave(event: ButtonClickEvent) {
    if(isValid) {
        commit()
        setReadOnly(true)
    }
  }

  private def onCancel(event: ButtonClickEvent) {
    discard()
    setReadOnly(true)
  }

  override def setItemDataSource(newDataSource: Item) {
    import scala.collection.JavaConverters._
    newDataSource match {
      case dataSource if(dataSource != null) =>
        super.setItemDataSource(dataSource, PersonList.columnDefinitions.map(col => col.field).asJavaCollection)
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