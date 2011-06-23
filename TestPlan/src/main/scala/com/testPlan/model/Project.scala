package com.testPlan.model

import util.Random
import com.sun.xml.internal.stream.buffer.sax.Features
import java.util.Date

object RandomSelector {
  val rand = new Random

  def next[T](items: Seq[T]): T = items(rand.nextInt(items length))
}

object Role extends Enumeration {
  val Admin = Value(0, "Admin")
  val Developer = Value(1, "Developer")
  val Tester = Value(2, "Tester")
}

class Person(val firstName: String, val lastName: String) {
  override def toString = firstName + " " + lastName
}

case class User(userName: String, password: String, role: Role.Value, person: Person)

class Scenario(var name: String, var estimatedHours: Option[Int] = None) {
}

class Feature(var name: String, var description: String, var storyPoints: Option[Int] = None) {
  var scenarios: Seq[Scenario] = Nil
}

class Iteration(var startDate: Date, var endDate: Date) {
  var features: Seq[Feature] = _
}

class Project(var name: String) {
  var iterations: Seq[Iteration] = Nil
  var owner: User = _
  var developers: Seq[User] = Nil

  override def toString = "Project Name: " + name + iterations.foreach(_.toString) + owner + developers.map(_.toString).mkString(", ")
}

object FakeProjects {
  import RandomSelector._
  val firstNames = List("Kelly", "Ryan", "Angela", "Stan", "Cheryl", "Mitch", "Cloe", "Bran")
  val lastNames = List("Davis", "Kennedy", "Tyson", "Wilson", "Hadly", "Sumner", "Forsythe", "Carlton")
  val roles = List(Role.Admin, Role.Developer, Role.Tester)
  val projectNouns = List("Tachyon", "Wave", "Particle", "Noise", "Electron", "Byte", "Mechanical", "Resonance")
  val projectVerbs = List("Emulsifier", "Emitter", "Distorter", "Refracter", "Amplifier", "Reducer", "Accelerator", "Expander", "Inverter")

  lazy val people: Seq[Person] = (1 to 1000).map(x => new Person(next(firstNames), next(lastNames)))

  lazy val users: Seq[User] = people.map {
    person =>
    val userName = person.firstName.charAt(0) + person.lastName
    val password = (1 to 8).map(value => next(('A' to 'Z').toList ::: ('a' to 'z').toList ::: ('0' to '9').toList)).mkString
    new User(userName, password, next(roles), person)
  }

  lazy val scenarios: Seq[Scenario] = (1 to 100).map(v => new Scenario("scenario" + v))

  lazy val features: Seq[Feature] = (1 to 100).map {
    v =>
    val result = new Feature("feature" + v, "My feature")
    result.scenarios = (1 to 5).map(_ => next(scenarios))
    result
  }

  lazy val projects: Seq[Project] = {
    def iterations = (1 to 20).map {
      x =>
      val iteration = new Iteration(new Date(), new Date())
      iteration.features = (1 to 5).map(_ => next(features))
      iteration
    }
    (1 to 5).map {
      _ =>
      val project = new Project(next(projectNouns) + " " + next(projectVerbs))
      project.owner = next(users)
      project.developers = (1 to 5).map(_ => next(users))
      project.iterations = iterations
      project
    }
  }

  /*
  var iterations: Seq[Iteration] = Nil
  var owner: User = _
  var developers: Seq[User] = Nil
   */

  def apply(): Seq[Project] = projects
}