/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.TerminalSetupDao;
import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.dao.model.DeviceSetupManagement;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceSetupManagementRepository;
import com.chatak.transit.afcs.server.util.StringUtil;

@Repository
public class TerminalSetupDaoImpl implements TerminalSetupDao {

	Logger logger = LoggerFactory.getLogger(TerminalSetupDaoImpl.class);

	@Autowired
	DeviceSetupManagementRepository deviceSetupRepository;

	@Autowired
	DeviceModelRepository deviceModelRepository; 

	@Override
	public DeviceModel getTerminalSetupRequest(DeviceSetupManagement request) {
		String info = " called from Dao Model";
		logger.debug(info);
		DeviceModel deviceModel = deviceModelRepository.findByDeviceIMEINumber(request.getDeviceOemId());
		if (!StringUtil.isNull(deviceModel)) {
			deviceSetupRepository.save(request);
		}
		return deviceModel;
	}
}
