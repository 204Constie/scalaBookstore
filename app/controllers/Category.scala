package controllers

import javax.inject.Inject

import dao.CategoryDAO
import models._
import play.api.mvc._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by constie on 27.05.2017.
  */
class Category @Inject() (categoryDAO: CategoryDAO) extends Controller {
  val Category = models.Category
  val CategoryREST = models.CategoryREST
  def categoriesGet = Action.async { implicit request =>
    categoryDAO.all map {
      categories => Ok(Json.toJson(categories))
    }
  }

  def categoriesPost = Action { implicit request =>
    val json:CategoryREST = request.body.asJson.get.as[CategoryREST]
    val cater = Category(id = 0, name = json.name)
    categoryDAO.insert(cater)
    Ok(json.name)
  }

  def categoryGet(id: Int) = TODO

  def categoryDelete(id: Int) = TODO

}
