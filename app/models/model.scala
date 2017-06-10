package models

/**
  * Created by constie on 27.05.2017.
  */
//class model {
//
//}

import play.api.libs.json.Json


case class Product(id: Int, title: String, author: String, categoryId: Int, price: Int)
object Product {
  implicit val ProductFormat = Json.format[Product]
}

case class CartItem(id: Int, productId: Int, orderId: Int, amount: Int)
object CartItem {
  implicit val CartItemFormat = Json.format[CartItem]
}

case class Order(id: Int, totalAmount: Int, shipment: String, payment: String)
object Order {
  implicit val OrderFormat = Json.format[Order]
}

case class Category(id: Int, name: String)
object Category {
  implicit val CategoryFormat = Json.format[Category]
}


case class ProductREST(title: String, author: String, categoryId: Int, price: Int)
object ProductREST {
  implicit val ProductFormat = Json.format[ProductREST]
}

case class CartItemREST(productId: Int, orderId: Int, amount: Int)
object CartItemREST {
  implicit val CartItemFormat = Json.format[CartItemREST]
}

case class OrderREST(totalAmount: Int, shipment: String, payment: String)
object OrderREST {
  implicit val OrderFormat = Json.format[OrderREST]
}

case class CategoryREST(name: String)
object CategoryREST {
  implicit val CategoryFormat = Json.format[CategoryREST]
}
