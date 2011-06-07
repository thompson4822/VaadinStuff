package com.example.addressBook.ui

import org.scalaVaadin._

object NavigationTree {
  val SHOW_ALL = "Show All"
  val SEARCH = "Search"
}

class NavigationTree extends Tree {
  import NavigationTree._
  addItem(SHOW_ALL)
  addItem(SEARCH)

}