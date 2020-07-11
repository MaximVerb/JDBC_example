package oefVaes;

import java.sql.*;
import java.util.Scanner;

public class SearchBeer {
    public static void main(String[] args) {
        String sql = "select b.Name, b.Alcohol, b.Price, br.Name, c.Category \n" +
                "from beers b inner join brewers br \n" +
                "on br.Id = b.BrewerId\n" +
                "inner join categories c \n" +
                "on c.Id = b.CategoryId\n"+
                "where Alcohol > 8";

        try (Connection con =
                     DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                             "root", getPassword());
             Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); //opent de query
             ResultSet rst = stm.executeQuery(sql))//vult query in en steekt het in een object

        {
            rst.afterLast();
            while (rst.previous()) {
                String beername = rst.getString("Name");
                double alcohol = rst.getDouble("Alcohol");
                double price = rst.getDouble("Price");
                String brewerName = rst.getString("Name");
                String species = rst.getString("Category");
                System.out.printf("%-90s%-10.2f%-10.2f%-90s%-20s%n", beername, alcohol, price,brewerName,species);
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
