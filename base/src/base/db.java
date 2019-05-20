package base;
import java.sql.SQLException;
import java.util.Arrays;

public class db {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		conn.Conn();
		conn.CreateDB();
		conn.ReadDB();
		conn.ReadSolution();
		conn.ReadRating();
		conn.EditProblemInDB(1, "test123", 1, 1);
		conn.CloseDB();
		drawTable a = new drawTable();
		/*String[] result = "12//34".split("//");
		System.out.print(Arrays.toString(result));*/
	}
}