package dao

/**
  * Created by constie on 23.04.2017.
  */

import javax.inject.Inject

import scala.concurrent.Future

import models.Product
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

class ProductDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Products = TableQuery[ProductsTable]

  def all(): Future[Seq[Product]] = db.run(Products.result)

  def insert(product: Product): Future[Unit] = db.run(Products += product).map{ _ => () }

  def delete(id: Int): Future[Unit] = db.run(Products.filter(_.id === id).delete).map(_ => ())

  private class ProductsTable(tag: Tag) extends Table[Product](tag, "Book"){
    def id = column[Int]("ID")

    def title = column[String]("TITLE")

    def author = column[String]("AUTHOR")

    def release_year = column[Int]("RELEASE_YEAR")

    def * = (id, title, author, release_year) <> (Product.tupled, Product.unapply _)
  }
}

