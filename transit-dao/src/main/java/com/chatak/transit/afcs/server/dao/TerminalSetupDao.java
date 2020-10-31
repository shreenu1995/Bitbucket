/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.dao.model.DeviceSetupManagement;

public interface TerminalSetupDao {

	DeviceModel getTerminalSetupRequest(DeviceSetupManagement request);

}