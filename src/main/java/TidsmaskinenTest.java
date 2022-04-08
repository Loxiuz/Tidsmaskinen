import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TidsmaskinenTest {
    public static void main(String[] args) {

        String host = "localhost";
        String port = "3306";
        String db = "tidsmaskinendb";

        String username = "root";
        String password = "X0aMu0";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + db;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);


            String manipulation = "";

            Statement statement = connection.createStatement();
            statement.executeUpdate(manipulation);

            statement.close();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
