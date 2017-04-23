package dao

/**
  * Created by constie on 23.04.2017.
  */

import javax.inject.Inject

import scala.concurrent.Future

import models.Order
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

class OrderDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Orders = TableQuery[OrdersTable]

  def all(): Future[Seq[Order]] = db.run(Orders.result)

  def insert(order: Order): Future[Unit] = db.run(Orders += order).map { _ => () }

  def delete(id: Int): Future[Unit] = db.run(Orders.filter(_.id === id).delete).map ( _ => () )

  private class OrdersTable(tag: Tag) extends Table[Order](tag, "Order"){
    def id = column[Int]("ID")

    def cartId = column[Int]("CART_ID")

    def * = (id, cartId) <> (Order.tupled, Order.unapply _)
  }
}

