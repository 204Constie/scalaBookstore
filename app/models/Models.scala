package models

/**
  * Created by constie on 10.04.2017.
  */

//class Models {

  case class Product(id: Int, title: String, author: String, release_year: Int)

  case class CartItem(id: Int, productId: Int)
//  object CartItem {
//
//    def all(): List[Book] = Nil
//
//    def create(label: String) {}
//
//    def delete(id: Int) {}
//
//  }

  case class Order(id: Int, cartId: Int)

//}
