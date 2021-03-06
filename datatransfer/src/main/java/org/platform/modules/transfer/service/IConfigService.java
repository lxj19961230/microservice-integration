package org.platform.modules.transfer.service;

import org.platform.utils.exception.BusinessException;

public interface IConfigService {

	/**
	 * 读取配置文件Key的Value
	 * @param key
	 * @return 
	 * @throws BusinessException
	 */
	public String readSystemConfigValue(String key) throws BusinessException;
	
	/**
	 * 刷新系统配置
	 * @throws BusinessException
	 */
	public void refreshSystemConfig() throws BusinessException;

}
