package com.example.addressBook.ui

import org.scalaVaadin._

object NavigationTree {
  val SHOW_ALL = "Show All"
  val SEARCH = "Search"
}

class NavigationTree(action: ItemClickEvent => Unit) extends Tree(action = action) {
  import NavigationTree._
  addItem(SHOW_ALL)
  addItem(SEARCH)
  setSelectable(true)
  setNullSelectionAllowed(false)
}