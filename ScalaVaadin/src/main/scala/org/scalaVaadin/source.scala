package org

import scala.xml.Elem
import com.vaadin.event.ItemClickEvent.ItemClickListener
import scala.collection.JavaConverters._
import scala.reflect.Manifest
import javax.sql.rowset.Predicate
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}

package object scalaVaadin {
  //type AbstractInMemoryContainer[ITEMIDTYPE, PROPERTYIDCLASS, ITEMCLASS] = com.vaadin.data.util.AbstractInMemoryContainer[ITEMIDTYPE, PROPERTYIDCLASS, ITEMCLASS]
  type Alignment = com.vaadin.ui.Alignment
  type BeanItem[A] = com.vaadin.data.util.BeanItem[A]
  type BeanItemContainer[A] = com.vaadin.data.util.BeanItemContainer[A]
  type ButtonClickEvent = com.vaadin.ui.Button#ClickEvent
  type CheckBox = com.vaadin.ui.CheckBox
  type Component = com.vaadin.ui.Component
  type CustomComponent = com.vaadin.ui.CustomComponent
  type Form = com.vaadin.ui.Form
  type HierarchicalContainer = com.vaadin.data.util.HierarchicalContainer
  type HorizontalLayout = com.vaadin.ui.HorizontalLayout
  type HorizontalSplitPanel = com.vaadin.ui.HorizontalSplitPanel
  type IntegerValidator = com.vaadin.data.validator.IntegerValidator
  type Item = com.vaadin.data.Item
  type ItemClickEvent = com.vaadin.event.ItemClickEvent
  type Label = com.vaadin.ui.Label
  type MenuBar = com.vaadin.ui.MenuBar
  type MenuItem = com.vaadin.ui.MenuBar#MenuItem
  type Panel = com.vaadin.ui.Panel
  type Property = com.vaadin.data.Property
  type Resource = com.vaadin.terminal.Resource
  type TextArea = com.vaadin.ui.TextArea
  type TextField = com.vaadin.ui.TextField
  type ThemeResource = com.vaadin.terminal.ThemeResource
  type TreeCollapseEvent = com.vaadin.ui.Tree#CollapseEvent
  type TreeCollapseListener = com.vaadin.ui.Tree.CollapseListener
  type TreeExpandEvent = com.vaadin.ui.Tree#ExpandEvent
  type TreeExpandListener = com.vaadin.ui.Tree.ExpandListener
  type VaadinApplication = com.vaadin.Application
  type ValueChangeEvent = com.vaadin.data.Property.ValueChangeEvent
  type VerticalLayout = com.vaadin.ui.VerticalLayout
  type VerticalSplitPanel = com.vaadin.ui.VerticalSplitPanel
  type Window = com.vaadin.ui.Window
  type WindowCloseEvent = Window#CloseEvent

  /*
  abstractInMemoryContainerToRichAbstractInMemoryContainer(container: com.vaadin.data.util.AbstractInMemoryContainer) = new RichInMemoryAbstractContainer(container)

  class RichInMemoryAbstractContainer(container: com.vaadin.data.util.AbstractInMemoryContainer) {
    def addContainerProperty() { container.addContainerProperty(<NEED TO PUT STUFF HERE>)}
  }
  */

  object Alignment {
    val ALIGNMENT_LEFT: Int = 1
    val ALIGNMENT_RIGHT: Int = 2
    val ALIGNMENT_TOP: Int = 4
    val ALIGNMENT_BOTTOM: Int = 8
    val ALIGNMENT_HORIZONTAL_CENTER: Int = 16
    val ALIGNMENT_VERTICAL_CENTER: Int = 32
  }

  ///////////////////////////////////////////////////////////////////
  //
  // RichAbstractComponent
  //
  ///////////////////////////////////////////////////////////////////
  implicit def abstractComponentToRichAbstractComponent(component: com.vaadin.ui.AbstractComponent) =
    new RichAbstractComponent(component)

  class RichAbstractComponent(component: com.vaadin.ui.AbstractComponent) {
    def setCaption(caption: Elem) { component.setCaption(caption.toString()) }
    def setDescription(description: Elem) { component.setDescription(description.toString()) }

    // Would like to add the following:
    def isVisible(predicate: => Boolean) { }
    def isEnabled(predicate: => Boolean) { }
    def isReadOnly(predicate: => Boolean) { }
  }

  ///////////////////////////////////////////////////////////////////
  //
  // RichAbstractField
  //
  ///////////////////////////////////////////////////////////////////
  implicit def abstractFieldToRichAbstractField(field: com.vaadin.ui.AbstractField) = new RichAbstractField(field)

  class RichAbstractField(field: com.vaadin.ui.AbstractField) {
    def addValueChangeEvent(action: ValueChangeEvent => Unit) = field.addListener(new ValueChangeListener(action))
  }

  ///////////////////////////////////////////////////////////////////
  //
  // RichAbstractOrderedLayout
  //
  ///////////////////////////////////////////////////////////////////
  implicit def abstractOrderedLayoutToRichAbstractOrderedLayout(layout: com.vaadin.ui.AbstractOrderedLayout) =
    new RichAbstractOrderedLayout(layout)

  class RichAbstractOrderedLayout(layout: com.vaadin.ui.AbstractOrderedLayout) {
    class AlignmentContext(component: com.vaadin.ui.Component) {
      import com.vaadin.ui.Alignment._
      def topLeft() { layout.setComponentAlignment(component, TOP_LEFT) }
      def topCenter() { layout.setComponentAlignment(component, TOP_CENTER) }
      def topRight() { layout.setComponentAlignment(component, TOP_RIGHT) }

      def middleLeft() { layout.setComponentAlignment(component, MIDDLE_LEFT) }
      def middleCenter() { layout.setComponentAlignment(component, MIDDLE_CENTER) }
      def middleRight() { layout.setComponentAlignment(component, MIDDLE_CENTER) }

      def bottomLeft() { layout.setComponentAlignment(component, BOTTOM_LEFT) }
      def bottomCenter() { layout.setComponentAlignment(component, BOTTOM_CENTER) }
      def bottomRight() { layout.setComponentAlignment(component, BOTTOM_RIGHT) }
    }
    def align(component: com.vaadin.ui.Component) = new AlignmentContext(component)
    def components: Iterator[Component] = layout.getComponentIterator.asScala
  }

  ///////////////////////////////////////////////////////////////////
  //
  // RichAbstractSplitPanel
  //
  ///////////////////////////////////////////////////////////////////
  implicit def abstractSplitPanelToRichAbstractSplitPanel(panel: com.vaadin.ui.AbstractSplitPanel) =
    new RichAbstractSplitPanel(panel)

  class RichAbstractSplitPanel(panel: com.vaadin.ui.AbstractSplitPanel) {
    def setSplitPosition(extent: UnitExtent, reverse: Boolean = false) {
      panel.setSplitPosition(extent.value.toInt, extent.unit, reverse)
    }
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
    override def toString = {
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

  implicit def sizableToRichSizable(sizable: com.vaadin.terminal.Sizeable) = new RichSizable(sizable)

  class RichSizable(component: com.vaadin.terminal.Sizeable) {
    def getVerticalExtent: UnitExtent = UnitExtent(component.getHeight, component.getHeightUnits)
    def setVerticalExtent(extent: UnitExtent) { component.setHeight(extent.value, extent.unit) }

    def getHorizontalExtent: UnitExtent = UnitExtent(component.getWidth, component.getWidthUnits)
    def setHorizontalExtent(extent: UnitExtent) { component.setWidth(extent.value, extent.unit) }

    def width: UnitExtent = UnitExtent(component.getWidth, component.getWidthUnits)
    def width_=(extent: UnitExtent) { component.setWidth(extent.value, extent.unit) }

  }

  class MenuBarCommand(action: MenuItem => Unit) extends com.vaadin.ui.MenuBar.Command {
    def menuSelected(selectedItem: MenuItem) { action(selectedItem) }
  }

  implicit def menuThingToRichMenuThing(menuThing: { def addItem(caption: String, icon: Resource, command: com.vaadin.ui.MenuBar.Command): MenuItem }) = new {
    def add(caption: String, action: MenuItem => Unit = null, icon: Resource = null): MenuItem = {
      if(action != null)
        menuThing.addItem(caption, icon, new MenuBarCommand(action))
      else
        menuThing.addItem(caption, icon, null)
    }
  }


/*
  object FProperty {
    def apply[T : Manifest](getter: => T) = new FProperty(getter)
    def apply[T : Manifest](getter: => T, setter: T => Unit) = new FProperty(getter, setter)
  }

  class FProperty[T : Manifest] private(getter: => T, setter: T => Unit = null) extends Property {
    def isReadOnly = setter == null
    def getType = {
      val result = classManifest[T].erasure
      println("Found the following type: " + result)
      result
    }
    def setValue(newValue: AnyRef) {
      if(setter != null) setter(newValue.asInstanceOf[T])
      println("Just set the value for this property!")
    }
    def getValue = {
      val value = getter
      value.asInstanceOf[AnyRef]
    }

    def setReadOnly(newStatus: Boolean) {}

    override def toString = getValue.toString
  }
*/

  class ValueChangeListener(f: ValueChangeEvent => Unit) extends com.vaadin.data.Property.ValueChangeListener {
    override def valueChange(event:ValueChangeEvent) { f(event) }
  }

  class HtmlLabel(text: String) extends Label(text, com.vaadin.ui.Label.CONTENT_XHTML) {
    def this(html:Elem) = { this(html.toString()) }
  }

  case class ColumnDefinition(field: String, title: String)

  class Table extends com.vaadin.ui.Table {
    def addListener(f: ValueChangeEvent => Unit) { addListener(new ValueChangeListener(f)) }
    def setColumns(columns: List[ColumnDefinition]) {
      setVisibleColumns(columns.map(_.field).toArray)
      setColumnHeaders(columns.map(_.title).toArray)
    }
  }

  object Theme {
    import com.vaadin.ui.themes._
    def reindeer = new Reindeer
    def runo = new Runo
    def base = new BaseTheme
    def liferay = new LiferayTheme
  }

  trait TreeExpand {
    self: Tree =>
    def onExpand(action: TreeExpandEvent => Unit) = {
      addListener(new TreeExpandListener {
        def nodeExpand(event: TreeExpandEvent) = action(event)
      })
    }
  }

  trait TreeCollapse {
    self: Tree =>
    def onCollapse(action: TreeCollapseEvent => Unit) = {
      addListener(new TreeCollapseListener {
        def nodeCollapse(event: TreeCollapseEvent) = action(event)
      })
    }
  }

  class Tree(caption: String = "", action: ItemClickEvent => Unit = null) extends com.vaadin.ui.Tree(caption) {
    if(action != null) {
      addListener(new ItemClickListener {
        def itemClick(event: ItemClickEvent) { action(event) }
      })
    }
  }

  implicit def componentContainerToRichComponentContainer(container: com.vaadin.ui.ComponentContainer) =
    new RichComponentContainer(container)

  class RichComponentContainer(container: com.vaadin.ui.ComponentContainer) {
    def addComponents(components: com.vaadin.ui.Component*) { components.foreach(container.addComponent(_)) }
  }

  implicit def textFieldToRichTextField(textField: TextField) = new RichTextField(textField)

  class RichTextField(textField: TextField) {
    val text = textField.getValue.toString
  }

  class ButtonClickListener(action: ButtonClickEvent => Unit) extends com.vaadin.ui.Button.ClickListener {
    def buttonClick(event: ButtonClickEvent) { action(event) }
  }

  class Button(text: String, action: ButtonClickEvent => Unit = null) extends com.vaadin.ui.Button(text, new ButtonClickListener(action))

  class WindowCloseListener(action: WindowCloseEvent => Unit) extends com.vaadin.ui.Window.CloseListener {
    def windowClose(event: WindowCloseEvent) { action(event) }
  }

}

