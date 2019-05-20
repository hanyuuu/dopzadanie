package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class conn {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;

	public static void Conn() throws ClassNotFoundException, SQLException {
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:info.s3db");

		System.out.println("База Подключена!");
	}

	public static void CreateDB() throws ClassNotFoundException, SQLException {
		statmt = conn.createStatement();
		statmt.execute(
				"CREATE TABLE if not exists 'solution' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'content' text);");
		statmt.execute("CREATE TABLE if not exists 'rating' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'rating' text);");
		statmt.execute("CREATE TABLE if not exists 'temp' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'temp' integer);");
		statmt.execute("CREATE TABLE if not exists 'problem' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'title' varchar(255) NOT NULL,"
						+ "'solutionID' int NOT NULL, 'ratingID' int NOT NULL, 'parent' int NOT NULL references problem(id),"
						+ "FOREIGN KEY (solutionID) REFERENCES solution(id), FOREIGN KEY (ratingID) REFERENCES rating(id));");
		System.out.println("Таблица создана или уже существует.");
	}

	public static void WriteDB() throws SQLException {
		String ohhellothere = "oh hello there";
		statmt.execute("INSERT INTO `rating` (`id`, `rating`) VALUES (NULL, 'Плохо'); ");
		statmt.execute("INSERT INTO `solution` (`id`, `content`) VALUES (NULL, 'Покушац'); ");
		statmt.execute(
				"INSERT INTO `problem` (`id`, `title`, `solutionID`, `ratingID`, `parent`) VALUES (NULL, 'Хочу кушац', '1', '1', '-1');");
		System.out.println("Таблица заполнена");
	}

	public static void EditProblemInDB(int id, String description, int solutionID, int ratingID)
			throws SQLException, ClassNotFoundException

	{
		//", solutionID="+solutionID+",ratingID="+ratingID+
		/*String[] result = ReadProblem(id).split("//");
		statmt.execute(
				"UPDATE solution SET content = '" + solutionID + "' WHERE id = " + Integer.parseInt(result[0]) + ";");
		statmt.execute("UPDATE rating SET rating = '" + ratingID + "' WHERE id = " + Integer.parseInt(result[1]) + ";");*/
		statmt.execute("UPDATE problem SET title = '" + description + "', solutionID="+solutionID+",ratingID="+ratingID+" WHERE id = " + id + ";");
		//UPDATE problem SET title = 'asd', solutionID=1, ratingID=1 WHERE id = "3";
	}

	public static String ReadProblem(int id) throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM `problem` WHERE problem.id = " + id + ";");
		int solutionID = 0;
		int ratingID = 0;
		while (resSet.next()) {
			solutionID = resSet.getInt("solutionID");
			ratingID = resSet.getInt("ratingID");
		}

		System.out.println("Таблица выведена");
		return solutionID + "//" + ratingID;
	}
	
	public static ArrayList<problem> problems = new ArrayList<problem>();

	public static void ReadDB() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery(
				"SELECT problem.id AS problemID, problem.title AS problemTitle, rating.rating AS problemRating, solution.content AS problemSolution FROM problem INNER JOIN rating ON problem.ratingID = rating.id INNER JOIN solution ON problem.solutionID = solution.id");

		while (resSet.next()) {
			int id = resSet.getInt("problemID");
			String title = resSet.getString("problemTitle");
			String solutionID = resSet.getString("problemSolution");
			String ratingID = resSet.getString("problemRating");

			solution tempsolution = new solution(id, solutionID);
			rating temprating = new rating(id, ratingID);
			problem temp = new problem(id, title, tempsolution, temprating);
			System.out.println("ID = " + id);
			System.out.println("title = " + title);
			System.out.println("solution = " + solutionID);
			System.out.println("rating = " + ratingID);
			System.out.println();
			problems.add(temp);
		}

		System.out.println("Таблица выведена");
	}

	public static ArrayList<solution> solutions = new ArrayList<solution>();
	public static ArrayList<rating> ratings = new ArrayList<rating>();

	public static void ReadSolution() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM solution;");
		System.out.println("Печатаю");
		while (resSet.next()) {
			int id = resSet.getInt("id");
			String content = resSet.getString("content");
			System.out.println(id + "    " + content);
			solution temp = new solution(id, content);
			solutions.add(temp);
		}
		System.out.println("konchil");

		System.out.println("Таблица выведена");
	}
	
	public static void ReadRating() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM rating;");
		System.out.println("Печатаю рейтинги");
		while (resSet.next()) {
			int id = resSet.getInt("id");
			String content = resSet.getString("rating");
			rating temp = new rating(id, content);
			ratings.add(temp);
			System.out.println(id + content);
		}
		System.out.println("konchil");

		System.out.println("Таблица выведена");
	}
	public static ArrayList<problem> getArr() {
		return problems;
	}

	public static ArrayList<solution> getSolutionArr() {
		return solutions;
	}
	public static ArrayList<rating> getRatingArr() {
		return ratings;
	}
	public static void CloseDB() throws ClassNotFoundException, SQLException {
		conn.close();
		statmt.close();
		resSet.close();

		System.out.println("Соединения закрыты");
	}

}