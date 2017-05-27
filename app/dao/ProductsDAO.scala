package dao

/**
  * Created by constie on 23.04.2017.
  */

import javax.inject.Inject

import scala.concurrent.{Future, ExecutionContext}

import models.{ProductREST, Product}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.json.Json
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent._
import ExecutionContext.Implicits.global

class ProductsDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Products = TableQuery[ProductTable]

  def all(implicit ec: ExecutionContext): Future[List[Product]] = {
    val query =  Products
    val results = query.result
    val futureProducts = db.run(results)
    futureProducts.map(
      _.map {
        z => Product(id = z.id, title = z.title, author = z.author, categoryId = z.categoryId, price = z.price)
      }.toList)
  }

//  val query = Products
//  val result = query.filter(_.id === id).result.headOption
//  val futureProduct = db.run(result)
//
//  futureProduct.map(
//    _.map {
//      a => ProductsREST(id = a.id, name = a.name, description = a.description, price = a.price, amount = a.amount, categoryId = a.categoryId, imageUrl = a.imageUrl)
//    }.toList.head
//  )

  def getById(id: Int): Future[Product] = {
    val query = Products
    val result = query.filter(_.id === id).result.headOption
    val futureProduct = db.run(result)
    futureProduct.map(
      _.map {
        z => Product(id = z.id, title = z.title, author = z.author, categoryId = z.categoryId, price = z.price)
      }.toList.head
    )
  }

//  def insert(products: ProductsREST) = {
//
//
//
//  }

//  val insertQuery = items returning items.map(_.id) into ((item, id) => item.copy(id = id))
//  val insertQuery = Products returning Products.map(_.id) into ((product, id) => product.copy(id = id))
//
//
//  def create(name: String, price: Double) : Future[Item] = {
//    val action = insertQuery += Item(0, name, price)
//    db.run(action)
//  }
//  def insert(products: ProductsREST): Future[Unit] = DBIO.seq(Products += products.map(_ => 0, _.title, _.author, _.release_year))
//
//{
////    val p = insertQuery += (0, products.title, products.author, products.release_year)
//    db.run(Products += (0, products.title, products.author, products.release_year)).map { _ => () }
//  }



//  class ProductDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
//    extends HasDatabaseConfigProvider[JdbcProfile] {
//    import driver.api._
//    private val Products = TableQuery[ProductsTable]
//    def all(): Future[Seq[Product]] = db.run(Products.result)
//    def insert(product: Product): Future[Unit] = db.run(Products += product).map { _ => () }

//  def all(implicit ec: ExecutionContext): Future[Seq[ProductsREST]] = {
//    val query = Products
//    val results = query.result
//    val futureCartItems = db.run(results)
//    futureCartItems.map(
//      _.map {
//        z => ProductsREST(title = z.title, author = z.author, release_year = z.release_year)
//      }.toList
//    )
//  }

  def insert(product: Product): Future[Unit] = db.run(Products += product).map { _ => () }
//
  def delete(id: Int): Future[Unit] = db.run(Products.filter(_.id === id).delete).map(_ => ())

  private class ProductTable(tag: Tag) extends Table[Product](tag, "TProducts"){
    def id = column[Int]("ID",O.AutoInc, O.AutoInc)

    def title = column[String]("TITLE")

    def author = column[String]("AUTHOR")

    def categoryId = column[Int]("CATEGORYID")

    def price = column[Int]("PRICE")

    def * = (id, title, author, categoryId, price) <> ((models.Product.apply _).tupled, models.Product.unapply)
  }
}

//class ProductsDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
//  extends HasDatabaseConfigProvider[JdbcProfile] {
//
//  import driver.api._
//
//  val Products = TableQuery[ProductsTable]
//
//  def all(implicit ec: ExecutionContext): Future[List[ProductsREST]] = {
//    val query =  Products
//    val results = query.result
//    val futureProducts = db.run(results)
//    futureProducts.map(
//      _.map {
//        a => ProductsREST(opis = a.opis, tytul = a.tytul)
//      }.toList)
//  }
//
//  class ProductsTable(tag: Tag) extends Table[Products](tag, "Products") {
//    def prodId = column[Long]("prodId",O.AutoInc, O.AutoInc)
//    def tytul = column[String]("tytul")
//    def opis = column[String]("opis")
//    def * = (prodId, tytul, opis) <> (models.Products.tupled, models.Products.unapply)
//  }
//
//}