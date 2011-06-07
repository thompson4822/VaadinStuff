package com.example.addressBook.data

import org.scalaVaadin._
import util.Random

object PersonContainer {
  val firstNames = List(
    "Peter", "Alice", "Joshua", "Mike", "Olivia",
		"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
		"Lisa", "Marge"
  )

  val lastNames = List(
    "Smith", "Gordon", "Simpson", "Brown",
		"Clavel", "Simons", "Verne", "Scott", "Allison", "Gates",
		"Rowling", "Barks", "Ross", "Schneider", "Tate"
  )

  val cities = List(
    "Amsterdam", "Berlin", "Helsinki",
		"Hong Kong", "London", "Luxemburg", "New York", "Oslo",
		"Paris", "Rome", "Stockholm", "Tokyo", "Turku"
  )

  val streets = List(
    "4215 Blandit Av.", "452-8121 Sem Ave",
		"279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave",
		"6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
		"161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.",
		"448-8295 Mi Avenue", "6419 Non Av.",
		"659-2538 Elementum Street", "2205 Quis St.",
		"252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.",
		"3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.",
		"2873 Nonummy Av.", "7342 Mi, Avenue",
		"539-3914 Dignissim. Rd.", "539-3675 Magna Avenue",
		"Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.",
		"141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.",
		"6897 Suscipit Rd.", "8336 Purus Avenue", "2603 Bibendum. Av.",
		"2870 Vestibulum St.", "Ap #722 Aenean Avenue",
		"446-968 Augue Ave", "1141 Ultricies Street",
		"Ap #992-5769 Nunc Street", "6690 Porttitor Avenue",
		"Ap #105-1700 Risus Street",
		"P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street",
		"414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
		"561-9262 Iaculis Avenue"
  )

  private val rand = new Random(this.hashCode())

  def randomFor(collection: List[String]): String = collection(rand.nextInt(collection.length))

  def randomPerson(): Person = {
    val firstName = randomFor(firstNames)
    val lastName = randomFor(lastNames)
    val email = (firstName + "." + lastName + "@vaadin.com").toLowerCase
    val phone = "+358 02 555" + (1 to 4).map(x => rand.nextInt(10).toString).mkString
    val postalCode = rand.nextInt(100000) match {
      case x if(x < 10000) => x + 10000
      case x => x
    }
    new Person(firstName, lastName, email, phone, randomFor(streets), postalCode, randomFor(cities))
  }

  def apply(): PersonContainer = {
    val container = new PersonContainer
    (1 to 100).map(x => randomPerson).foreach(person => container.addItem(person))
    container
  }

}

class PersonContainer extends BeanItemContainer(classOf[Person]) with Serializable