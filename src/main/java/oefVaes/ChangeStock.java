package oefVaes;

import java.sql.*;
import java.util.Scanner;

public class ChangeStock {
    public static void main(String[] args) {
        String sqlQ = "update beers \n" +
                "set stock = (stock+50) \n" +
                "where lower(name) = 'jupiler'; "; //je verandert de stock van het bier jupiler
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                "root",getPassword()))

        {
            Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int result = stm.executeUpdate(sqlQ);//na deze statement, nog eens dubbel gecheckt bij sql, daar ook gewijzigd
            System.out.println(result);
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
