package dao

/**
  * Created by constie on 23.04.2017.
  */

import javax.inject.Inject

import scala.concurrent.{Future, ExecutionContext}

import models.{CartItem, CartItemREST}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global


class CartItemDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val CartItems = TableQuery[CartItemsTable]

  def all(implicit ec: ExecutionContext): Future[Seq[CartItemREST]] = {
    val query = CartItems
    val results = query.result
    val futureCartItems = db.run(results)
    futureCartItems.map(
      _.map {
        z => CartItemREST(orderId = z.orderId, productId = z.productId, amount = z.amount)
      }.toList
    )
  }

  def insert(cartItem: CartItem): Future[Unit] = db.run(CartItems += cartItem).map { _ => () }

  def delete(id: Int): Future[Unit] = db.run(CartItems.filter(_.id === id).delete).map(_ => ())


  private class CartItemsTable(tag: Tag) extends Table[CartItem](tag, "TCartItem"){
    def id = column[Int]("ID",O.AutoInc, O.AutoInc)

    def productId = column[Int]("PRODUCTID")

    def orderId = column[Int]("ORDERID")

    def amount = column[Int]("AMOUNT")

    def * = (id, productId, orderId, amount) <> ((models.CartItem.apply _).tupled, models.CartItem.unapply _)
  }
}

//(Person.apply _).tupled