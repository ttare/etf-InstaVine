package ba.etf.instavine.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ba.etf.instavine.dao.CommentDao;
import ba.etf.instavine.dao.ContentDao;
import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.TagDao;
import ba.etf.instavine.dao.UserActionCommentDao;
import ba.etf.instavine.dao.UserActionContentDao;
import ba.etf.instavine.dao.UserActionDao;
import ba.etf.instavine.dao.UserActionTypeDao;
import ba.etf.instavine.dao.UserDao;
import ba.etf.instavine.dao.impl.UserDaoImpl;
import ba.etf.instavine.utils.DaoConfiguration;


public class JDBCDaoFactory implements DaoFactory
{
	private static JDBCDaoFactory instance = null;
	
	private static Connection connection = null;
	
	
	private JDBCDaoFactory()
	{
		DaoConfiguration configuration = DaoConfiguration.getInstance();
		
		String url = null, 
			   user = null, 
			   password = null;
		
		try 
		{
			url = configuration.getEntry("db.url", true);
	        user = configuration.getEntry("db.username", true);
	        password = configuration.getEntry("db.password", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
        	throw new DaoException("JDBCDaoFactory failed " + ex.getMessage());
        }
	}
	
	public static DaoFactory getInstance() {
		if (instance == null) {
			instance = new JDBCDaoFactory();
		}
		return instance;
	}
	

	@Override
	public Connection getConnection() {
		return connection;
	}
	

	@Override
	public CommentDao getCommentDao() {
		return new CommentDaoImpl(this);
	}

	@Override
	public ContentDao getContentDao() {
		return new ContentDaoImpl(this);
	}

	@Override
	public UserActionCommentDao getUserActionCommentDao() {
		return new UserActionCommentDaoImpl(this);
	}

	@Override
	public UserActionContentDao getUserActionContentDao() {
		return new UserActionContentDaoImpl(this);
	}

	@Override
	public UserActionDao getUserActionDao() {
		return new UserActionDaoImpl(this);
	}
	
	@Override
	public UserActionTypeDao getUserActionTypeDao() {
		return new UserActionTypeDaoImpl(this);
	}

	@Override
	public UserDao getUserDao() {
		return new UserDaoImpl(this);
	}

	@Override
	public TagDao getTagDao() {
		return new TagDaoImpl(this);
	}	
}