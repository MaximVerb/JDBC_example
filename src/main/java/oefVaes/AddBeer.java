package oefVaes;

import java.sql.*;
import java.util.Scanner;

public class AddBeer {
    public static void main(String[] args) {
    String sqlQ =       "insert into beers (Name, Alcohol, Price, Stock)"+
                        "values ('ABeer', 12,3,100)";
    try (Connection con =   DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                            "root",getPassword());
         Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE))

    {
        int result = stm.executeUpdate(sqlQ,Statement.RETURN_GENERATED_KEYS);
        System.out.println(result);
        try(ResultSet rs = stm.getGeneratedKeys())
        {
            if (rs.next())
            {int id = rs.getInt(1);
                System.out.println(id);}
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