package controllers;

import java.util.List;

import java.util.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

import play.mvc.*;
import play.mvc.Http.RequestBody;

public class BooksController extends Controller {
	
	public Result index() {
		String sql = "SELECT B.title, CONCAT(A.fname,' ', A.lname) AS author, B.publication_year, B.reading "
		 		   + "FROM Books AS B, Writes AS W, Authors AS A "
		 		   + "WHERE B.bid=W.bid AND W.aid=A.aid";

		List<SqlRow> row = Ebean.createSqlQuery(sql).findList();
			
		return ok(views.html.booksIndex.render(row));
	}
	
	public Result newBook() {
    	return ok(views.html.newBook.render());
    }

    public Result create() {
        RequestBody body = request().body();
        java.util.Map<String, String[]> m = body.asFormUrlEncoded();
        String title = m.get("title")[0];
        int publication_year = Integer.parseInt(m.get("publication_year")[0]);
        int reading = Integer.parseInt(m.get("reading")[0]);
        String fname = m.get("fname")[0];
        String lname = m.get("lname")[0];
        String genre = m.get("genre")[0];

        SqlUpdate bookInsert = Ebean.createSqlUpdate("INSERT INTO Books (title, publication_year, reading) VALUES (:t, :py, :r)");
        bookInsert.setParameter("t", title);
        bookInsert.setParameter("py", publication_year);
        bookInsert.setParameter("r", reading);
        bookInsert.execute();

        SqlUpdate authorInsert = Ebean.createSqlUpdate("INSERT INTO Authors (fname,lname) VALUES (:fn, :ln)");
        authorInsert.setParameter("fn", fname);
        authorInsert.setParameter("ln", lname);
        authorInsert.execute();

        SqlUpdate genreInsert = Ebean.createSqlUpdate("INSERT INTO Genres (name) VALUES (:genre)");
        genreInsert.setParameter("genre", genre);
        genreInsert.execute();

        String bookQuery = "SELECT B.bid FROM Books AS B WHERE title = :t1";
        int bid = Ebean.createSqlQuery(bookQuery)
        			 .setParameter("t1", title)
        			 .findList()
        			 .get(0)
        			 .getInteger("bid");

        String genreQuery = "SELECT G.gid FROM Genres AS G WHERE name = :g1";
        int gid = Ebean.createSqlQuery(genreQuery)
        			 .setParameter("g1", genre)
        			 .findList()
        			 .get(0)
        			 .getInteger("gid");

        String authorQuery = "SELECT A.aid FROM Authors AS A WHERE fname = :fn1 AND lname = :ln1";
        int aid = Ebean.createSqlQuery(authorQuery)
        			 .setParameter("fn1", fname)
        			 .setParameter("ln1", lname)
        			 .findList()
        			 .get(0)
        			 .getInteger("aid");

        SqlUpdate writesInsert = Ebean.createSqlUpdate("INSERT INTO Writes (aid,bid) VALUES (:a1, :b1)");
        writesInsert.setParameter("a1", aid);
        writesInsert.setParameter("b1", bid);
        writesInsert.execute();

        SqlUpdate isOfGenreInsert = Ebean.createSqlUpdate("INSERT INTO is_of_genre (bid,gid) VALUES (:b1, :g1)");
        isOfGenreInsert.setParameter("b1", bid);
        isOfGenreInsert.setParameter("g1", gid);
        isOfGenreInsert.execute();
        
        return index();
    }
	
	
	
	
}
