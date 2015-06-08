package example

import org.widok._
import org.widok.html._

object Main extends PageApplication {
  val keyword = Var("")

  implicit def string2layout(selector: String) = new {
    val (id, cssClasses) = selector.split('.').map(_.trim).filterNot(_.isEmpty).toList.partition(_.startsWith("#")) match {
      case (id :: Nil, classes) => (id.substring(1), classes)
      case (Nil, classes) => ("", classes)
      case (_ :: _, _) => throw new IllegalArgumentException("Too many ids for :" + selector)
    }
    def >>>[T <: Widget[T]](widget: T) = widget.id(id).css(cssClasses: _*)
  }

  def view() = "#main-page" >>> div(
    "#main-form.form.search" >>> div(
      ".search-panel" >>> div(
        ".search" >>> text()
          .bind(keyword).placeholder("Search")
      )
    ),
    div()
  )

  def ready() {
    log("Page loaded.")
  }


}
