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

  def objects = TODO

  def predicates = TODO

  def subjects = TODO
}
