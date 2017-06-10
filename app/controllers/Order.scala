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

  def ordersPost = Action.async { implicit request =>
    val json:OrderREST = request.body.asJson.get.as[OrderREST]
    val cater = Order(id = 0, totalAmount = json.totalAmount, shipment = json.shipment, payment = json.payment)
    orderDAO.insert(cater) map {
      order => Ok(Json.toJson(order))
    }
//    Ok(Json.toJson("success"))
  }
//
//  def ordersPost = Action { implicit request =>
//    val json:OrderREST = request.body.asJson.get.as[OrderREST]
//    val cater = Order(id = 0, totalAmount = json.totalAmount)
//    orderDAO.insert(cater)
//    Ok(Json.toJson("success"))
//  }


  def orderGet(id: Int) = Action.async { implicit request =>
    orderDAO.getOrderById(id).map {
      order => Ok(Json.toJson(order))
    }
  }

  def orderDelete(id: Int) = Action { implicit request =>
    orderDAO.delete(id)
    Ok(Json.toJson("success"))
  }

//  def update(cartItemId: Int) = Action { implicit request =>
//    var jsondata: CartItemsREST = request.body.asJson.get.as[CartItemsREST]
//    var itemToUpdate = CartItem(id=jsondata.id, productId=jsondata.productId, orderId=jsondata.orderId, quantity=jsondata.quantity)
//    cartItemDAO.update(id=jsondata.id, itemToUpdate)
//    Ok(Json.toJson("{success: true}"))
//  }

  def orderUpdate() = Action { implicit request =>
    val orderitem:models.Order = request.body.asJson.get.as[models.Order]
//    val cater = Order(id = 0, totalAmount = json.totalAmount)
    orderDAO.update(orderitem)
    Ok(Json.toJson("success"))
  }

}
