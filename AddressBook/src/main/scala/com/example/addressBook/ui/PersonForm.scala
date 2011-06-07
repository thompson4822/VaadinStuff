package com.example.addressBook.ui

import org.scalaVaadin._

class PersonForm extends Form {
  private val save = new Button("Save")
  private val cancel = new Button("Cancel")

  addField("First Name", new TextField("First Name"))
  addField("Last Name", new TextField("Last Name"))
  val footer = new HorizontalLayout
  footer.setSpacing(true)
  footer.addComponents(save, cancel)
  setFooter(footer)

}