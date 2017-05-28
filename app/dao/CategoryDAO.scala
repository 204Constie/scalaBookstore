package dao

/**
  * Created by constie on 27.05.2017.
  */

import javax.inject.Inject

import scala.concurrent.{Future, ExecutionContext}

import models.{Category, CategoryREST}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.json.Json
import ExecutionContext.Implicits.global

class CategoryDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Categories = TableQuery[CategoryTable]

  def all(implicit ec: ExecutionContext): Future[Seq[Category]] = {
    val query = Categories
    val results = query.result
    val futureCartItems = db.run(results)
    futureCartItems.map(
      _.map {
        z => Category(id = z.id, name = z.name)
      }.toList
    )
  }

  def getCategoryById(id: Int): Future[Category] = {
    val query = Categories
    val result = query.filter(_.id === id).result.headOption
    val futureCategory = db.run(result)
    futureCategory.map(
      _.map {
        z => Category(id = z.id, name = z.name)
      }.toList.head
    )
  }

  def insert(category: Category): Future[Unit] = db.run(Categories += category).map { _ => () }

  def delete(id: Int): Future[Unit] = db.run(Categories.filter(_.id === id).delete).map(_ => ())

  private class CategoryTable(tag: Tag) extends Table[Category](tag, "TCategory"){
    def id = column[Int]("ID",O.AutoInc, O.AutoInc)

    def name = column[String]("NAME")

    def * = (id, name) <> ((models.Category.apply _).tupled, models.Category.unapply _)
  }
}
