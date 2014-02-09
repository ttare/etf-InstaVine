package ba.etf.instavine.service.impl;

import java.util.List;

import ba.etf.instavine.dao.DaoFactory;
import ba.etf.instavine.dao.UserActionDao;
import ba.etf.instavine.dao.UserActionTypeDao;
import ba.etf.instavine.dao.UserDao;
import ba.etf.instavine.dao.impl.JDBCDaoFactory;
import ba.etf.instavine.models.User;
import ba.etf.instavine.models.UserAction;
import ba.etf.instavine.models.UserActionType;
import ba.etf.instavine.service.UserService;

public class UserServiceImpl implements UserService 
{
	private DaoFactory daoFactory;
	private UserDao userDao;
	private UserActionDao userActionDao;
	private UserActionTypeDao userActionTypeDao;;
	
	public UserServiceImpl() 
	{
		daoFactory = JDBCDaoFactory.getInstance();
		userDao = daoFactory.getUserDao();
		userActionDao = daoFactory.getUserActionDao();
	}

	@Override
	public User find(int id) 
	{
		User user = userDao.find(id);
		if (user == null) {
			return null;
		}
				
		return user;
	}

	@Override
	public User getUser(String usernameOrEmail) 
	{
		User user = userDao.find(usernameOrEmail);
		if (user == null) {
			return null;
		}

		return user;
	}

	@Override
	public List<UserAction> getUserActions(User user, int offset, int limit) 
	{
		List<UserAction> userActions = userActionDao.findAllByUser(user.getId(), offset, limit);
		
		for(UserAction userAction : userActions)
		{
			int typeId = userAction.getUserActionType().getId();
			
			UserActionType actionType = userActionTypeDao.find(typeId);
			userAction.setUserActionType(actionType);
		}
		
		return userActions;
	}

	@Override
	public List<User> findAll(int offset, int limit) {
		return userDao.findAll(offset, limit);
	}

	@Override
	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public int numberOfUsers() {
		return userDao.count();
	}
}
