package com.example.addressBook.ui

import com.example.addressBook.Application
import org.scalaVaadin._
import com.example.addressBook.data.Person

object PersonList {
  val columnDefinitions = List(ColumnDefinition("firstName", "First name"),
    ColumnDefinition("lastName", "Last name"),
    ColumnDefinition("email", "Email"),
    ColumnDefinition("phoneNumber", "Phone number"),
    ColumnDefinition("streetAddress", "Street address"),
    ColumnDefinition("postalCode", "Postal code"),
    ColumnDefinition("city", "City")
  )
}

class PersonList(dataSource: BeanItemContainer[Person], listener: ValueChangeEvent => Unit) extends Table {
  setSizeFull()
  setContainerDataSource(dataSource)
  setColumns(PersonList.columnDefinitions)
  setSelectable(true)
  setImmediate(true)
  addListener(listener)

}