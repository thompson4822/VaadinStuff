package com.example.addressBook.ui

import org.scalaVaadin._

class ListView(personList: PersonList, personForm: PersonForm) extends VerticalSplitPanel {
  setFirstComponent(personList)
  setSecondComponent(personForm)
  setSplitPosition(40)

}