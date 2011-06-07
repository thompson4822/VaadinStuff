package org

import com.vaadin.ui.{Label, Window}
import scala.xml.Elem
import io.BytePickle.Def
import scalaVaadin.UnitExtent
import java.lang.Boolean

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 5/30/11
 * Time: 7:38 AM
 * To change this template use File | Settings | File Templates.
 */

package object scalaVaadin {

  type BeanItem[A] = com.vaadin.data.util.BeanItem[A]
  type BeanItemContainer[A] = com.vaadin.data.util.BeanItemContainer[A]
  type ButtonClickEvent = com.vaadin.ui.Button#ClickEvent
  type CheckBox = com.vaadin.ui.CheckBox
  type Component = com.vaadin.ui.Component
  type CustomComponent = com.vaadin.ui.CustomComponent
  type Form = com.vaadin.ui.Form
  type HorizontalLayout = com.vaadin.ui.HorizontalLayout
  type HorizontalSplitPanel = com.vaadin.ui.HorizontalSplitPanel
  type IntegerValidator = com.vaadin.data.validator.IntegerValidator
  type Label = com.vaadin.ui.Label
  type Panel = com.vaadin.ui.Panel
  type TextArea = com.vaadin.ui.TextArea
  type TextField = com.vaadin.ui.TextField
  type Tree = com.vaadin.ui.Tree
  type VaadinApplication = com.vaadin.Application
  type ValueChangeEvent = com.vaadin.data.Property.ValueChangeEvent
  type VerticalLayout = com.vaadin.ui.VerticalLayout
  type VerticalSplitPanel = com.vaadin.ui.VerticalSplitPanel
  type Window = com.vaadin.ui.Window
  type WindowCloseEvent = Window#CloseEvent

  implicit def intToUnitExtent(value: Int) = floatToUnitExtent(value)

  implicit def floatToUnitExtent(value: Float) = new {
    def cm: UnitExtent = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_CM)
    def em = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_EM)
    def ex = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_EX)
    def in = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_INCH)
    def mm = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_MM)
    def percent = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE)
    def pc = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_PICAS)
    def px = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_PIXELS)
    def pt = UnitExtent(value, com.vaadin.terminal.Sizeable.UNITS_POINTS)
  }

  case class UnitExtent(value: Float, unit: Int) {
    override def toString() = {
      value.toString + (unit match {
        case com.vaadin.terminal.Sizeable.UNITS_CM => "cm"
        case com.vaadin.terminal.Sizeable.UNITS_EM => "em"
        case com.vaadin.terminal.Sizeable.UNITS_EX => "ex"
        case com.vaadin.terminal.Sizeable.UNITS_INCH => "in"
        case com.vaadin.terminal.Sizeable.UNITS_MM => "mm"
        case com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE => "%"
        case com.vaadin.terminal.Sizeable.UNITS_PICAS => "pc"
        case com.vaadin.terminal.Sizeable.UNITS_PIXELS => "px"
        case com.vaadin.terminal.Sizeable.UNITS_POINTS => "pt"
      })
    }
  }

  class RichSizable(s: com.vaadin.terminal.Sizeable) {
    def setWidth(extent: UnitExtent) = s.setWidth(extent.value, extent.unit)
    def setHeight(extent: UnitExtent) = s.setHeight(extent.value, extent.unit)
  }

  implicit def abstractSplitPanelToRichAbstractSplitPanel(panel: com.vaadin.ui.AbstractSplitPanel) =
    new RichAbstractSplitPanel(panel)

  class RichAbstractSplitPanel(panel: com.vaadin.ui.AbstractSplitPanel) {
    def setSplitPosition(extent: UnitExtent, reverse: Boolean = false) {
      panel.setSplitPosition(extent.value.toInt, extent.unit, reverse)
    }
  }

  implicit def abstractComponentToRichAbstractComponent(component: com.vaadin.ui.AbstractComponent) =
    new RichAbstractComponent(component)

  class RichAbstractComponent(component: com.vaadin.ui.AbstractComponent) {
    def setWidth(extent: UnitExtent) = component.setWidth(extent.toString())
  }

/*
  object Sizable {
    val CM: Int = com.vaadin.terminal.Sizeable.UNITS_CM
    val EM: Int = com.vaadin.terminal.Sizeable.UNITS_EM
    val EX: Int = com.vaadin.terminal.Sizeable.UNITS_EX
    val IN: Int = com.vaadin.terminal.Sizeable.UNITS_INCH
    val MM: Int = com.vaadin.terminal.Sizeable.UNITS_MM
    val PERCENT: Int = com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE
    val PICAS: Int = com.vaadin.terminal.Sizeable.UNITS_PICAS
    val PX: Int = com.vaadin.terminal.Sizeable.UNITS_PIXELS
    val PTS: Int = com.vaadin.terminal.Sizeable.UNITS_POINTS
  }
*/

  class ValueChangeListener(f: ValueChangeEvent => Unit) extends com.vaadin.data.Property.ValueChangeListener
  {
    override def valueChange(event:ValueChangeEvent) { f(event) }
  }

  class HtmlLabel(text: String) extends Label(text, Label.CONTENT_XHTML) {
    def this(html:Elem) = { this(html.toString) }
  }

  case class ColumnDefinition(field: String, title: String)

  class Table extends com.vaadin.ui.Table {
    def addListener(f: ValueChangeEvent => Unit) { addListener(new ValueChangeListener(f)) }
    def setColumns(columns: ColumnDefinition*) = {
      setVisibleColumns(columns.map(_.field).toArray)
      setColumnHeaders(columns.map(_.title).toArray)
    }
  }


  implicit def componentContainerToRichComponentContainer(container: com.vaadin.ui.ComponentContainer) =
    new RichComponentContainer(container)

  class RichComponentContainer(container: com.vaadin.ui.ComponentContainer) {
    def addComponents(components: com.vaadin.ui.Component*) { components.foreach(container.addComponent) }
  }

  implicit def textFieldToRichTextField(textField: TextField) = new RichTextField(textField)

  class RichTextField(textField: TextField) {
    val text = textField.getValue.toString
  }

  class ButtonClickListener(action: ButtonClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener {
    def buttonClick(event: ButtonClickEvent) { action(event) }
  }

  class Button(text: String, action: ButtonClickEvent => Unit = null) extends com.vaadin.ui.Button(text, new ButtonClickListener(action))

  class WindowCloseListener(action: WindowCloseEvent => Unit) extends Window.CloseListener {
    def windowClose(event: WindowCloseEvent) { action(event) }
  }

/*
  implicit def intToSizeUnit(value: Int) = new SizeUnit(value)

  class SizeUnit(size: Int) {
    def px = size + "px"
    def pt = size + "pt"
    def pc = size + "pc"
    def em = size + "em"
    def ex = size + "ex"
    def mm = size + "mm"
    def cm = size + "cm"
    def in = size + "in"
    def percent = size + "%"
  }
*/
}

