package controllers

import javax.inject.Inject

import dao.CartItemsDAO
import models.{CartItemREST, CartItem}
import play.api.mvc._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by constie on 23.04.2017.
  */
class CartItems @Inject() (cartitemsDAO: CartItemsDAO) extends Controller {

  def cartitemsGet = Action.async { implicit request =>
    cartitemsDAO.all map {
      cartitems => Ok(Json.toJson(cartitems))
    }
  }

  def cartitemsPost = Action { implicit request =>
    val json:CartItemREST = request.body.asJson.get.as[CartItemREST]
    val cater = CartItem(id = 0, productId = json.productId , orderId = json.orderId, amount = json.amount)
    cartitemsDAO.insert(cater)
    Ok(Json.toJson("{success: true}"))
  }

  def cartitemGet(id: Int) =  Action.async { implicit request =>
    cartitemsDAO.getCartItemById(id).map {
      cartitem => Ok(Json.toJson(cartitem))
    }
  }

  def cartitemDelete(id: Int) = Action { implicit request =>
    cartitemsDAO.delete(id)
    Ok(Json.toJson("success"))
  }

}

//object Application @Inject() (productsDAO: ProductsDAO) extends Controller {
//
//  def index = Action.async { implicit request =>
//    productsDAO.all map {
//      products => Ok(Json.toJson(products))
//    }
//  }
//
//}