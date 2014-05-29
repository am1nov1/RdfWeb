package controllers

import java.io._
import models.Triple
import models.Triplestore
import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {

    //Ok(views.html.index(string_builder.toString()))
    Ok("TODO")
  }

  def objects = Action {

    val objects = Triplestore.objects()

    //Ok(views.html.index(string_builder.toString()))
    Ok(objects.mkString(", "))
  }

  def predicates = TODO

  def subjects = Action {

    val subjects = Triplestore.subjects()

    //Ok(views.html.index(string_builder.toString()))
    Ok(subjects.mkString(", "))
  }
}
