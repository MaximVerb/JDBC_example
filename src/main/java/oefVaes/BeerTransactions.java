package oefVaes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class BeerTransactions {
private static final String UPDATE =
        "update beers set stock = ? where name =?"; // een string-plaeholder mag niet tussen enkele aanhalingtekens, dat geeft fouten

public static void main(String[] args) throws Exception {
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
            "root", getPassword())) {
        con.setAutoCommit(false);   //hiermee zijn meerdere sql-commando's na elkaar mogelijk
        con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); //dirty read / phantom read / non-repeatable read

        try (PreparedStatement uq = con.prepareStatement(UPDATE))
        {
            uq.setInt(1, 100);
            uq.setString(2,"Jupiler");
            uq.executeUpdate();//transactie start
            uq.clearParameters();
            //con.commit();             als je een savepoint wil maken in je transacties
            //con.setSavepoint();       dan dien je eerst te committen
            uq.setInt(1, 200);
            uq.setString(2,"Adler");
            uq.executeUpdate();
            con.commit();//transactie stopt
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            con.rollback();
        }
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

