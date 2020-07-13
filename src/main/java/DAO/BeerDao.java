package DAO;

import java.sql.SQLException;

public interface BeerDao {
    public Beer getBeerById (int id) throws BeerException;
    public void updateBeer (Beer beer) throws BeerException, SQLException;
}
