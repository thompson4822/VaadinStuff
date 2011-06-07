package com.example.addressBook.ui

import com.example.addressBook.Application
import org.scalaVaadin._
import com.example.addressBook.data.Person

class PersonList(dataSource: BeanItemContainer[Person], listener: ValueChangeEvent => Unit) extends Table {
  setSizeFull()
  setContainerDataSource(dataSource)
  setColumns(
    ColumnDefinition("firstName", "First name"),
    ColumnDefinition("lastName", "Last name"),
    ColumnDefinition("email", "Email"),
    ColumnDefinition("phoneNumber", "Phone number"),
    ColumnDefinition("streetAddress", "Street address"),
    ColumnDefinition("postalCode", "Postal code"),
    ColumnDefinition("city", "City"))
  setSelectable(true)
  setImmediate(true)
  addListener(listener)

}