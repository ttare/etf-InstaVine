package ba.etf.instavine.dao;

import java.sql.Connection;

public interface DaoFactory 
{
	public abstract Connection getConnection();
	
	public abstract CommentDao getCommentDao();
	
	public abstract ContentDao getContentDao();
        
    public abstract UserActionCommentDao getUserActionCommentDao();
    
    public abstract UserActionContentDao getUserActionContentDao();
    
    public abstract UserActionDao getUserActionDao();
    
    public abstract UserActionTypeDao getUserActionTypeDao();
    
    public abstract UserDao getUserDao();

    public abstract TagDao getTagDao();
}