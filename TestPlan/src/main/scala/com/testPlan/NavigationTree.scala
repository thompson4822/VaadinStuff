package com.testPlan

import model._
import org.scalaVaadin._

object NavigationTree {
  val CAPTION = 'caption
  val PROJECT = 'project
  val ITERATION = 'iteration
  val FEATURE = 'feature
  val SCENARIO = 'scenario
}

class NavigationTree extends Tree("Projects") {
  import NavigationTree._
  private val projects = FakeProjects()

  val container = {
    val result = new HierarchicalContainer
    result.addContainerProperty(CAPTION, classOf[String], null)
    result.addContainerProperty(PROJECT, classOf[Project], null)
    result.addContainerProperty(ITERATION, classOf[Iteration], null)
    result.addContainerProperty(FEATURE, classOf[Feature], null)
    result.addContainerProperty(SCENARIO, classOf[Scenario], null)
    result
  }

  def populateContainer() = projects foreach {
    project =>
    val id = container.addItem(project)
    if(id != null) {
      id.getItemProperty(CAPTION).setValue(project.name)
      id.getItemProperty(PROJECT).setValue(project)
      createIterationLeafs(project, id)
    }
  }

  def createIterationLeafs(project: Project, parentId: Item) = project.iterations.foreach {
    iteration =>
    val id = container.addItem(iteration)
    if(id != null) {
      val caption = iteration.startDate + " - " + iteration.endDate
      id.getItemProperty(CAPTION).setValue(caption)
      id.getItemProperty(ITERATION).setValue(iteration)
      container.setParent(id, parentId)
      container.setChildrenAllowed(parentId, true)
    }
  }

  populateContainer()
  setContainerDataSource(container)
  setItemCaptionPropertyId(CAPTION)

  // Code for the tree to go here...
}