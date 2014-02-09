package ba.etf.instavine.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ba.etf.instavine.dao.DaoException;
import ba.etf.instavine.dao.mapper.RowMapper;

public final class DaoUtil 
{	
	public static ResultSet executeQuery(Connection connection, String query, Object... arguments) throws DaoException
	{
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		
		try 
		{
			preparedStatement = connection.prepareStatement(query);
			
			for (int i = 0; i < arguments.length; i++) {
		        preparedStatement.setObject(i + 1, arguments[i]);
		    }
			
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			throw new DaoException("executeQuery failed. " + e.getMessage());
		}
		
		return resultSet;
	}
	
	
	public static int executeUpdate(Connection connection, String query, Object... arguments) throws DaoException
	{
		boolean isInsert = query.startsWith("INSERT");
		
		int affectedRows = 0;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;
		
		try 
		{
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			for (int i = 0; i < arguments.length; i++) {
		        preparedStatement.setObject(i + 1, arguments[i]);
		    }
			
			affectedRows = preparedStatement.executeUpdate();
	        
			if (isInsert) 
			{
				generatedKeys = preparedStatement.getGeneratedKeys();
				
				if (generatedKeys.next()) {
					affectedRows = generatedKeys.getInt(1);
				}
			}

		} catch (SQLException e) {
			throw new DaoException("executeUpdate failed. " + e.getMessage());
		} finally 
		{	
			try 
			{
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (generatedKeys != null) {
					generatedKeys.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return affectedRows;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> executeQuery(Connection connection, RowMapper rowMapper, String query, Object... arguments) throws DaoException
	{
		List<T> result = new ArrayList<T>();
		
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			for (int i = 0; i < arguments.length; i++) {
		        preparedStatement.setObject(i + 1, arguments[i]);
		    }
			
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
	        while(resultSet.next()) {
	        	result.add((T) rowMapper.map(resultSet));
	        }
	        
		} catch (SQLException e) {
			throw new DaoException("executeQuery failed. " + e.getMessage());
		}
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T executeQueryReturnOne(Connection connection, RowMapper rowMapper, String query, Object... arguments) throws DaoException
	{
		T result = null;
		
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			for (int i = 0; i < arguments.length; i++) {
		        preparedStatement.setObject(i + 1, arguments[i].toString());
		    }
			
		    ResultSet resultSet = preparedStatement.executeQuery();
		        
	        if (resultSet.next()) {
	        	result = (T) rowMapper.map(resultSet);
	        }
	        
		} catch (SQLException e) {
			throw new DaoException("executeQueryReturnOne failed. " + e.getMessage());
		}
		
		return result;
	}
	
	
	public static DateFormat dateFormat = null;
	
	public static java.util.Date string2Date(String date) throws ParseException
	{
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		return dateFormat.parse(date);
	}
	
	
	public static java.sql.Date utilDate2SqlDatw(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
}
