
package za.ac.tut.bl;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thapelo Mkhwanazi
 * @param <T>
 */
public interface DAOInterface<T> 
{
    public Boolean add(T t)throws SQLException;
    public boolean delete(Integer id) throws SQLException;
    public boolean update(T t) throws SQLException;
    public T get(Integer id) throws SQLException;
    public List<T> getAll() throws SQLException;
}
