package oefVaes;

import java.sql.*;
import java.util.Scanner;

public class OefStock50 {
    public static void main(String[] args) {
        String sqlQ = "select * from beers where alcohol > ?";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                "root",getPassword()))

        {
            PreparedStatement stm = con.prepareStatement(sqlQ,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stm.setInt(1,11);
            ResultSet rst = stm.executeQuery();

            while(rst.next())
            {
                rst.updateDouble("Stock", rst.getDouble("Stock") + 50);
                rst.updateRow();
            }
            rst.close();
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
