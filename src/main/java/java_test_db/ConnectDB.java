package java_test_db;
import java.sql.*;
import java.util.Scanner;

public class ConnectDB {
    public static void main(String[] args) {
        String sql = "SELECT Name, Alcohol, Price FROM Beers WHERE name like 'A%'";
        try (Connection con =
                     DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                             "root", getPassword());
             Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); //opent de query
             ResultSet rst = stm.executeQuery(sql))//vult query in en steekt het in een object

        {
            while (rst.next()) {
                String beername = rst.getString("Name");
                double alcohol = rst.getDouble("Alcohol");
                double price = rst.getDouble("Price");
                System.out.printf("%s %s %s %n", beername, alcohol, price);
            }
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static String getPassword () {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Gelieve je paswoord in te geven");
        String password = keyboard.nextLine();
        keyboard.close();
        return password;
    }
}

