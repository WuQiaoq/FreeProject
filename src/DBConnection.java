import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static void main(String[] args){
        String db_url = "jdbc:mysql://localhost:3306/FreeProject";
        String user = "player";
        String password = "player";

        try
        {
            Connection con = DriverManager.getConnection(db_url, user, password);
            con.close();
        }catch(Exception e){
            System.out.println("La conexión de la base de datos ha fallado");
        }
    }

}
