package com.example.bookproject

import org.scalaVaadin._
import scala.collection.JavaConverters._

class BookProjectApplication extends VaadinApplication {
  val detailLayout = new VerticalLayout

  override def init() {
    val book = new Book("1-234567-89-0", author = "John Rizzo", title = "Black Belt is not for Dummies", price = 0)
    val mainWindow = new Window("BookProject Application")
    setMainWindow(mainWindow)

    val titleLabel = Label(<h1>Book Application</h1>)
    mainWindow.addComponent(titleLabel)

    displayTable(bookList, mainWindow)
    mainWindow.addComponent(detailLayout)

  }

  def displayTable(bookList: List[Book], window: Window) {
    // Don't really care for the following, seems like Table should be generically typed and set with a container data source
    // on construction
    val table = new Table
    table.setSelectable(true)
    table.setContainerDataSource(new BeanItemContainer(bookList.asJava))
    table.setVerticalExtent(100 px)
    table.addListener({
      _ =>
      val selectedBook = table.getValue.asInstanceOf[Book]
      displayBook(selectedBook)
    })
    table setImmediate true
    window addComponent table
  }

  val bookList: List[Book] = List (
    new Book(title = "Black Belt is not for Dummies", author = "John Rizzo", price = 100),
    new Book(title = "Vaadin for Ninjas", author = "Joonas Lehtinen", price = 200),
    new Book(title = "More Love With Vaadin", author = "John Rizzo", price = 300)
  )

  def displayBook(book: Book) {
    detailLayout.removeAllComponents()
    detailLayout.addComponents(
      new Label(book.title),
      new Label(book.author),
      new Label("$" + book.price),
      new Button("edit", _ => editBook(book) )
    )
  }

  def editBook(book: Book) {
    detailLayout.removeAllComponents()
    val priceField = new TextField("Price", book.price.toString)
    priceField.addValidator(new IntegerValidator("Price must be an Integer"))

    val titleField = new TextField("Title", book.title)
    val authorField = new TextField("Author", book.author)

    def saveEvent(event: ButtonClickEvent) {
      book.title = titleField.text
      book.author = authorField.text
      if(!priceField.isValid)
        getMainWindow showNotification "Please fix the form value problems"
      else {
        book.price = priceField.text.toInt
        displayBook(book)
        getMainWindow showNotification "Your record was saved!"
      }
    }

    detailLayout.addComponents(
      titleField,
      authorField,
      priceField,
      new Button("save", saveEvent)
    )
  }

}