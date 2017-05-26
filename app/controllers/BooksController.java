package controllers;

import java.util.List;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlRow;

import play.mvc.*;

public class BooksController extends Controller {
	
	public Result index() {
		String sql = "SELECT B.title, CONCAT(A.fname,' ', A.lname) AS author, B.publication_year, B.reading "
		 		   + "FROM Books AS B, Writes AS W, Authors AS A "
		 		   + "WHERE B.bid=W.bid AND W.aid=A.aid";

		List<SqlRow> row = Ebean.createSqlQuery(sql).findList();
			
		return ok(views.html.booksIndex.render(row));
	}
	
	
	
	
}
