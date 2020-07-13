package DAO;

import java.sql.*;
import java.util.ArrayList;

public class BeerDaoImpl implements BeerDao {
    private String url;
    private String user;
    private String password;

    public BeerDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Beer getBeerById(int id) throws BeerException {
        try(Connection con = getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * from beers where id = ?")) {
            pstm.setInt(1, id);                              //de id is eigenlijk de primary key van de SQL database
            try (ResultSet rst = pstm.executeQuery()) {         //voert de query uit
                if (rst.next()) {                               //indien waar maak nieuw object bier van bierklasse
                    Beer beer = new Beer();                     //haalt alle info uit de database via de kolomnamen
                    beer.setId(id);                             //je kunt eventueel ook kolomindexen gebruiken
                    beer.setName(rst.getString("Name"));      //vb kolom 1 , kolom 2 enz.
                    beer.setPrice(rst.getFloat("Price"));       //maar als je database dan verandert gaat je code fout gaan
                    beer.setAlcohol(rst.getFloat("Alcohol"));   //daarom zijn kolomnamen flexibeler
                    beer.setStock(rst.getInt("Stock"));
                    return beer;
                }
                else
                    {
                    return null;
                    }
            }
        }                                            // dit zijn 2 try met maar één catch, omdat de 2de try
        catch (SQLException e) {                     // zich binnenin de eerste try vind
            throw new BeerException(e);              // volgende elementen moeten in een try with resources staan
        }                                            // connection + statement + resultset
    }                                                // dan worden ze automatisch achteraf afgesloten via de interface
                                                    //AutoCloseAble() --> moet je niet expliciet con.close() of
                                                    // of rst.close () aanroepen om je database te beschermen
    public ArrayList<Object> getBrewerByZipcode (String zipcode) {
        String sql = "select * from Brewers where zipcode = ?";
        ArrayList<Object> brewerList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, zipcode);
            try (ResultSet rst = pstm.executeQuery()) {
                while (rst.next()) {
                    brewerList.add(rst.getObject(1));            //geeft enkel de ID's mee
                    brewerList.add(rst.getObject(2));              //geeft de 2de kolomindex mee
                }
                return brewerList;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

        @Override
    public void updateBeer(Beer beer) throws BeerException{
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE Beers SET Name = ?, Price = ?," +
                     "Alcohol = ?, Stock = ?, WHERE Id = ?"))
        {
            pstm.setString(1, beer.getName());
            pstm.setFloat(2,beer.getPrice());
            pstm.setFloat(3, beer.getAlcohol());
            pstm.setInt(4, beer.getStock());
            pstm.setInt(5,beer.getId());
            pstm.executeUpdate();
        }
        catch (SQLException e) {
            throw new BeerException(e);
        }

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
