package oefVaes;

import java.sql.*;
import java.util.Scanner;

public class oefPrepared {
    public static void main(String[] args) {
        String sql = "Select * from beers where alcohol > ?";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                "root",getPassword());
            PreparedStatement stm = con.prepareStatement(sql))

        {
            stm.setInt(1,8);
            ResultSet rst = stm.executeQuery();

            ResultSetMetaData metaData = rst.getMetaData();
            int aantalKolommen = metaData.getColumnCount();
            for (int i = 1; i<=aantalKolommen; i++)
            {
                switch(metaData.getColumnName(i))
                {
                    case "Name":System.out.printf("%-90s", metaData.getColumnName(i));
                    break;
                    case "Alcohol":
                    case "Price":System.out.printf("%-10s", metaData.getColumnName(i));
                    break;
                }
            }

            System.out.println();

            while (rst.next()) {
                String beername = rst.getString("Name");
                double alcohol = rst.getDouble("Alcohol");
                double price = rst.getDouble("Price");
                System.out.printf("%-90s%-10.2f%-10.2f%n", beername, alcohol, price);
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
