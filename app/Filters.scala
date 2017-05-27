import javax.inject.Inject
import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

/**
  * Created by constie on 08.05.2017.
  */
class Filters @Inject() (corsFilter: CORSFilter) extends HttpFilters {
  def filters = Seq(corsFilter)
}
