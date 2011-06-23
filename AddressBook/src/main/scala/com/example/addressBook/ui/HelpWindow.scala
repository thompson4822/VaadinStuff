package com.example.addressBook.ui

import org.scalaVaadin._

object HelpWindow {
  val helpHtmlSnippet = <span>This is an application built during
    <strong><a href="http://dev.vaadin.com">Vaadin</a></strong> tutorial.  Hopefully it doesn't
    need any real help</span>
}

class HelpWindow extends Window {
  setCaption("Address Book help")
  addComponent(new HtmlLabel(HelpWindow.helpHtmlSnippet))

}

class SharingOptions extends Window {
  setModal(true)
  this.setHorizontalExtent(50 percent)
  this.width = (50 percent)
  //this.width = 50 percent
  center()

  setCaption("Sharing options")
  this.addComponents(
    new Label(
      "With these settings you can modify contact sharing options. (non-functional, example of modal dialog)"
    ),
    new CheckBox("Gmail"),
    new CheckBox(".Mac"),
    new Button("close")
  )
}