package com.example.addressBook

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 6/5/11
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */

import data.PersonContainer
import org.scalaVaadin._
import ui._

class Application extends VaadinApplication {
  private val newContact = new Button("Add Contact", onAddContact)
  private val search = new Button("Search", _ => horizontalSplit.setSecondComponent(searchView))
  private val share = new Button("Share", _ => getMainWindow addWindow sharingOptions)
  private val help = new Button("Help", _ => getMainWindow addWindow helpWindow)

  private val navigationTree = new NavigationTree(navigateTreeAction)

  def navigateTreeAction(event: ItemClickEvent) {
    horizontalSplit.setSecondComponent(event.getItemId match {
      case NavigationTree.SHOW_ALL => listView
      case NavigationTree.SEARCH => searchView
    })
  }

  val dataSource = PersonContainer()

  private val personForm: PersonForm = new PersonForm
  private lazy val personList: PersonList = new PersonList(dataSource, {
    _ =>
      val item = personList.getItem(personList.getValue)
      if(item != personForm.getItemDataSource)
        personForm.setItemDataSource(item)
  })

  private lazy val listView = new ListView(personList, personForm)

  private lazy val searchView = new SearchView(this)

  private lazy val sharingOptions = new SharingOptions

  private lazy val helpWindow = new HelpWindow

  private val horizontalSplit = {
    val result = new HorizontalSplitPanel
    result.setFirstComponent(navigationTree)
    result.setSecondComponent(listView)
    result
  }

  override def init {
    buildMainLayout
  }

  private def buildMainLayout {
    setMainWindow(new Window("Address Book Demo Application"))
    val layout = new VerticalLayout
    layout.setSizeFull()

    layout.addComponents(createToolbar, horizontalSplit)

    // Allocate all available extra space to the horizontal split panel
    layout.setExpandRatio(horizontalSplit, 1)

    // Set initial split position so we can have a 200 pixel menu to the left
    horizontalSplit.setSplitPosition(200 px)
    getMainWindow setContent layout

    getMainWindow addWindow helpWindow


  }

  val createToolbar = {
    val lo = new HorizontalLayout
    lo.addComponents(newContact, search, share, help)
    lo
  }

  private def onAddContact(event: ButtonClickEvent) {}

}