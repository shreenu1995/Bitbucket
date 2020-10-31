package com.chatak.transit.afcs.server.dao;

public interface CustomErrorResolution {

	public boolean deviceTypeNameValidation(String deviceTypeName);
    
	public boolean fareNameValidation(String fareName);
	
	public boolean isValidUser(String userID);

	public boolean isValidAdminUser(String adminUserID);

	public boolean ticketNoValidation(String ticketNo);

	public boolean isValidDeviceModelNumber(String paymentMode);

	boolean passwordValidation(String pass);

	public boolean deviceModel(String model);

	public boolean deviceType(Long deviceTypeId);

	boolean routeNameValidation(String routeName);

	public boolean validateOrganizationId(Long orgId);

	boolean deviceTypeIdValidation(Long deviceTypeId);

	boolean validateDepotId(Long depotId);

	public boolean validateStopName(String stopName);
	
	public boolean validateDepotName(String depotName);

	public boolean deviceManufacturerIdValidation(Long deviceManufacturerCode);

	public boolean deviceManufacturerValidation(String deviceManufacturer);

	public boolean validateProductId(Long id);
	
	public boolean validateDiscountId(Long valueOf);
	
	public boolean validateOperatorId(Long operatorId);
	
	public boolean validateOrganizationName(String organizationName);
	
	public boolean validatePtoName(String ptoName);
	
	public boolean validateOperatorName(String operatorName);
	
	public boolean validateVehicleTypeName(String vehicleTypeName);
	
	public boolean validateVehicleManufacturerName(String vehicleManufName);
	
	public boolean validateVehicleModelName(String vehicleModelName);

	public boolean isValidUserName(String userName);

	public boolean isDeviceModelExists(Long deviceModel);
	
	public boolean deviceIMEINumber(String deviceIMEINumber);

	public boolean isUserActive(String userId);

	boolean stageIdValidation(Long stageId);
	
	boolean validatePtoMasterID(String ptoMasterId);

	boolean ptoIdValidation(Long ptoId);
}
