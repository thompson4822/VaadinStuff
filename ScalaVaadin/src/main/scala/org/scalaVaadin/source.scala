package org

import com.vaadin.ui.{Label, Window}
import scala.xml.Elem
import java.lang.Boolean
import com.vaadin.event.ItemClickEvent.ItemClickListener
import scala.collection.JavaConverters._

package object scalaVaadin {
  type Alignment = com.vaadin.ui.Alignment
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
  type Item = com.vaadin.data.Item
  type ItemClickEvent = com.vaadin.event.ItemClickEvent
  type Label = com.vaadin.ui.Label
  type Panel = com.vaadin.ui.Panel
  type TextArea = com.vaadin.ui.TextArea
  type TextField = com.vaadin.ui.TextField
  type VaadinApplication = com.vaadin.Application
  type ValueChangeEvent = com.vaadin.data.Property.ValueChangeEvent
  type VerticalLayout = com.vaadin.ui.VerticalLayout
  type VerticalSplitPanel = com.vaadin.ui.VerticalSplitPanel
  type Window = com.vaadin.ui.Window
  type WindowCloseEvent = Window#CloseEvent

  // setComponentAlignment(component left center)

  object Alignment {
    val ALIGNMENT_LEFT: Int = 1
    val ALIGNMENT_RIGHT: Int = 2
    val ALIGNMENT_TOP: Int = 4
    val ALIGNMENT_BOTTOM: Int = 8
    val ALIGNMENT_HORIZONTAL_CENTER: Int = 16
    val ALIGNMENT_VERTICAL_CENTER: Int = 32
  }

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

  implicit def abstractOrderedLayoutToRichAbstractOrderedLayout(layout: com.vaadin.ui.AbstractOrderedLayout) =
    new RichAbstractOrderedLayout(layout)

  class RichAbstractOrderedLayout(layout: com.vaadin.ui.AbstractOrderedLayout) {
    class AlignmentContext(component: com.vaadin.ui.Component) {
      import com.vaadin.ui.Alignment._
      def topLeft = layout.setComponentAlignment(component, TOP_LEFT)
      def topCenter = layout.setComponentAlignment(component, TOP_CENTER)
      def topRight = layout.setComponentAlignment(component, TOP_RIGHT)

      def middleLeft = layout.setComponentAlignment(component, MIDDLE_LEFT)
      def middleCenter = layout.setComponentAlignment(component, MIDDLE_CENTER)
      def middleRight = layout.setComponentAlignment(component, MIDDLE_CENTER)

      def bottomLeft = layout.setComponentAlignment(component, BOTTOM_LEFT)
      def bottomCenter = layout.setComponentAlignment(component, BOTTOM_CENTER)
      def bottomRight = layout.setComponentAlignment(component, BOTTOM_RIGHT)
    }
    def align(component: com.vaadin.ui.Component) = new AlignmentContext(component)
    def components: Iterator[Component] = layout.getComponentIterator.asScala
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

  class ValueChangeListener(f: ValueChangeEvent => Unit) extends com.vaadin.data.Property.ValueChangeListener {
    override def valueChange(event:ValueChangeEvent) { f(event) }
  }

  class HtmlLabel(text: String) extends Label(text, Label.CONTENT_XHTML) {
    def this(html:Elem) = { this(html.toString) }
  }

  case class ColumnDefinition(field: String, title: String)

  class Table extends com.vaadin.ui.Table {
    def addListener(f: ValueChangeEvent => Unit) { addListener(new ValueChangeListener(f)) }
    def setColumns(columns: List[ColumnDefinition]) = {
      setVisibleColumns(columns.map(_.field).toArray)
      setColumnHeaders(columns.map(_.title).toArray)
    }
  }

  class Tree(action: ItemClickEvent => Unit = null) extends com.vaadin.ui.Tree {
    if(action != null) {
      addListener(new ItemClickListener {
        def itemClick(event: ItemClickEvent) { action(event) }
      })
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

}

