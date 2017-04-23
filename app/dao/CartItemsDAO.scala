package dao

/**
  * Created by constie on 23.04.2017.
  */

import javax.inject.Inject

import scala.concurrent.Future

import models.CartItem
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global


class CartItemsDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val CartItems = TableQuery[CartItemsTable]

  def all(): Future[Seq[CartItem]] = db.run(CartItems.result)

  def insert(cartItem: CartItem): Future[Unit] = db.run(CartItems += cartItem).map { _ => () }

  def delete(id: Int): Future[Unit] = db.run(CartItems.filter(_.id === id).delete).map(_ => ())

  private class CartItemsTable(tag: Tag) extends Table[CartItem](tag, "CartItem"){
    def id = column[Int]("ID")

    def productId = column[Int]("PRODUCT_ID")

    def * = (id, productId) <> (CartItem.tupled, CartItem.unapply _)
  }
}

