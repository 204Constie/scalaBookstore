package controllers

import javax.inject.Inject

import dao.OrderDAO
import models._
import play.api.mvc._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by constie on 23.04.2017.
  */
class Order @Inject() (orderDAO: OrderDAO) extends Controller {
  val Order = models.Order
  val OrderREST = models.OrderREST
  def ordersGet = Action.async { implicit request =>
    orderDAO.all map {
      orders => Ok(Json.toJson(orders))
    }
  }

  def ordersPost = Action { implicit request =>
    val json:OrderREST = request.body.asJson.get.as[OrderREST]
    val cater = Order(id = 0, totalAmount = json.totalAmount)
    orderDAO.insert(cater)
    Ok(Json.toJson("{success: true}"))
  }

  def orderGet(id: Int) = TODO

  def orderDelete(id: Int) = TODO

}
