package org.platform.modules.oauth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.platform.modules.abstr.dao.GenericDAO;
import org.platform.modules.abstr.entity.Query;
import org.platform.modules.abstr.service.impl.GenericServiceImpl;
import org.platform.modules.oauth.dao.UserDAO;
import org.platform.modules.oauth.entity.User;
import org.platform.modules.oauth.service.IUserService;
import org.platform.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements IUserService {

	@Resource(name = "userDAO")
	private UserDAO userDAO = null;
	
	@Override
	public GenericDAO<User, Long> obtainDAOInstance() {
		return userDAO;
	}
	
	@Override
	protected void preHandle(Object object) throws BusinessException {
		if (object instanceof User) {
			User user = (User) object;
			Long id = user.getId();
			if (null != id) {
				User dbUser = userDAO.readDataByPK(id);
				if (null == dbUser) {
					throw new BusinessException("user not exists!");
				}
			}
		}
	}
	
	@Override
	public void insert(Object object) throws BusinessException {
		super.insert(object);
	}
	
	@Override
	public User readUserByUsername(String username) throws BusinessException {
		if (StringUtils.isBlank(username)) throw new BusinessException("username is null");
		Query query = new Query();
		query.addCondition("username", username);
		return userDAO.readDataByCondition(query);
	}
	
	@Override
	public User readUserByUsernameAndPassword(String username, String password) throws BusinessException {
		if (StringUtils.isBlank(username)) throw new BusinessException("username is null");
		if (StringUtils.isBlank(password)) throw new BusinessException("password is null");
		Query query = new Query();
		query.addCondition("username", username);
		query.addCondition("password", password);
		return userDAO.readDataByCondition(query);
	}

}
