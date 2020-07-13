package DAO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BeerDaoImpl aDao = new BeerDaoImpl("jdbc:mysql://localhost:3306/beersdb?serverTimezone=UTC",
                "root",getPassword());

        System.out.println(aDao.getBrewerByZipcode("9700"));

    }

    static String getPassword () {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Gelieve je paswoord in te geven");
        String password = keyboard.nextLine();
        keyboard.close();
        return password;
    }
}
