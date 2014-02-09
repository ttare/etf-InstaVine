package ba.etf.instavine.dao;

public class DaoException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	public DaoException(String message) {
        super(message);
    }
}
