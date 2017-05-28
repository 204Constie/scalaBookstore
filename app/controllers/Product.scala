package controllers

import javax.inject.Inject

import dao.ProductsDAO
import models._
import play.api.mvc._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by constie on 23.04.2017.
  */
class Product @Inject() (productsDAO: ProductsDAO) extends Controller {
  val Product = models.Product
  val ProductREST = models.ProductREST
  def productsGet = Action.async { implicit request =>
    productsDAO.all map {
      products => Ok(Json.toJson(products))
    }
  }

  def productsPost = Action { implicit request =>
    val json:ProductREST = request.body.asJson.get.as[ProductREST]
    val prod = Product(id = 0, title = json.title, author = json.author, categoryId = json.categoryId, price = json.price)
    productsDAO.insert(prod)
    Ok(json.author)
  }

  def productGet(id: Int) = Action.async { implicit request =>
    productsDAO.getById(id) map {
      product => Ok(Json.toJson(product))
    }
  }

  def productDelete(id: Int) = Action { implicit request =>
    productsDAO.delete(id)
    Ok(Json.toJson("success"))
  }

}

//package controllers
//
//import javax.inject.Inject
//
//import daos.ProductsDAO
//import models.ProductsREST
//import play.api.libs.json.Json
//import play.api.mvc._
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
//
//class Application @Inject() (productsDAO: ProductsDAO) extends Controller {
//
//  def index = Action.async { implicit  request =>
//    productsDAO.all map {
//      products => Ok(Json.toJson(products))
//    }
//  }
//
//  def handlepost = Action { implicit request =>
//    var json:ProductsREST = request.body.asJson.get.as[ProductsREST]
//    Ok(json.opis)
//  }
//}

//object Application @Inject() (productsDAO: ProductsDAO) extends Controller {
//
//  def index = Action.async { implicit request =>
//    productsDAO.all map {
//      products => Ok(Json.toJson(products))
//    }
//  }
//
//}
//def handlepost = Action { implicit request =>
//  var json:ProductsREST = request.body.asJson.get.as[ProductsREST]
//  Ok(json.opis)
//}