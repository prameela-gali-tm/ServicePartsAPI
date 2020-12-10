package com.toyota.scs.serviceparts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.ServicePartsApplication;
import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.entity.RoutePathNodeEntity;
import com.toyota.scs.serviceparts.entity.ShipmentEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.CaseModel;
import com.toyota.scs.serviceparts.model.Message;
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.model.ShipmentModel;
import com.toyota.scs.serviceparts.model.VendorsModel;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.repository.RoutePathNodeRepositroy;
import com.toyota.scs.serviceparts.repository.ShipmentRepositroy;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.util.ServicePartConstant;

@Service
public class ShipmentService {

	@Autowired
	ShipmentRepositroy shipmentRepositroy;
	@Autowired
	CaseRepositroy caseRepositroy;
	@Autowired
	RoutePathNodeRepositroy routePathNodeRepository;
	static String EMPTY = "";

	public Page getAllShipmentDetails(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

		Page<ShipmentEntity> pagedResult = shipmentRepositroy.findAll(paging);

		return pagedResult;
	}

	public Page getAllShipmentDetailsByVendorAndStatus(Integer pageNo, Integer pageSize, String sortBy,
			List<String> vendorCode) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

		List<String> status = new ArrayList<String>();
		status.add("DRAFT");
		status.add("SHIPMENT LOAD");

		Page<ShipmentEntity> pagedResult = shipmentRepositroy.findAllByVendorCodeInAndStatusIn(vendorCode, status,
				paging);

		return pagedResult;
	}

	public ModelApiResponse saveShipmentDetails(ShipmentModel shipmentModel) throws ParseException {
		// TODO Auto-generated method stub
		ModelApiResponse message=new ModelApiResponse();
		Message mes = new Message();
		Map<String, Message> mesMap = new HashMap<String, Message>();
		ShipmentEntity shipEntity = new ShipmentEntity();
		List<RoutePathNodeEntity> routepathNode = new ArrayList<RoutePathNodeEntity>();
		trailerNumberValidation(shipmentModel, mesMap, mes);
		shipEntity.setTrailer(shipmentModel.getTrailerNumber());

		routeValidation(shipmentModel, mesMap, mes);
		shipEntity.setRoute(shipmentModel.getRoute());

		runValidation(shipmentModel, mesMap, mes);
		// if Run not equal to special characters then only convert to Long
		if(isNumeric(shipmentModel.getRun()))
		{
		shipEntity.setRun(Long.parseLong(shipmentModel.getRun()));
		}

		planPickupDateValidation(shipmentModel, mesMap, mes);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date modifiedDate = formatter2.parse(shipmentModel.getPlanPickupDate());
		shipEntity.setModifiedDate(modifiedDate);

		sealNumberValidation(shipmentModel, mesMap, mes);
		shipEntity.setTrackingNumber(shipmentModel.getSealNumber());

		supplierFirstNameValidation(shipmentModel, mesMap, mes);
		shipEntity.setSupplierFirstName(shipmentModel.getSupplierFirstName());

		supplierLastNameValidation(shipmentModel, mesMap, mes);
		shipEntity.setSupplierLastName(shipmentModel.getSupplierLastName());

		lpCodeValidation(shipmentModel, mesMap, mes);
		shipEntity.setScacCode(shipmentModel.getLpCode());

		driverFirstNameValidation(shipmentModel, mesMap, mes);
		shipEntity.setDriverFirstName(shipmentModel.getDriverFirstName());

		driverLastNameValidation(shipmentModel, mesMap, mes);
		shipEntity.setDriverLastName(shipmentModel.getDriverLastName());

		ShipmentEntity shipmentEntity = shipmentRepositroy.save(shipEntity);

		if (shipmentModel != null && shipmentModel.getVendors().size() > 0) {
			for (VendorsModel vendor : shipmentModel.getVendors()) {
				if (vendor != null && vendor.getCaseModel().size() > 0) {

					shipmentIDValidation(vendor, mesMap, mes);

					for (CaseModel cases : vendor.getCaseModel()) {
						if (cases != null) {
							caseNumberValidation(cases, mesMap, mes);
							CaseEntity caseEntity = caseRepositroy.findByCaseNumber(cases.getCaseNumber());

							if (caseEntity != null && vendor.getShipmentID().length()>0 && isNumeric(vendor.getShipmentID())) {
								// caseList.add(caseEntity.getId());
								RoutePathNodeEntity routePathNodeEntity = new RoutePathNodeEntity();
								routePathNodeEntity.setCaseId(caseEntity.getId());
								routePathNodeEntity.setShipmentId(Long.parseLong(vendor.getShipmentID()));
								routePathNodeEntity.setRoutePathId(shipmentEntity.getId());

								routepathNode.add(routePathNodeEntity);
							} else {
								// case number doesn't exist
								pushMessage(mes, ServicePartConstant.CASE_NUMBER_NOT_EXISTS);

							}
						}

					}

				}
			}
			if(mesMap.isEmpty()) {
			routePathNodeRepository.saveAll(routepathNode);
			}
			else
			{
				message.setMessages(new ArrayList<Message>(mesMap.values()));
			}

		}
		return message;
	}

	private void caseNumberValidation(CaseModel cases, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = cases.getCaseNumber();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		int days = caseNumberDaysValidation(cases.getCaseNumber());
		pushMessage(mes, caseNumberValid(cases.getCaseNumber(),days));
	}

	public int caseNumberDaysValidation(String caseNumber) {
		CaseEntity caseEntity = caseRepositroy.findByCaseNumber(caseNumber);
		int diffDays = -1;
		if (caseEntity != null) {
			if(caseEntity.getStatus().equalsIgnoreCase("LOAD BUILD")) {
				String format = "MM/dd/yyyy hh:mm a";
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				try {
					Date dateObj1 = sdf.parse(sdf.format(caseEntity.getModifiedDate()));
					Date dateObj2 = sdf.parse(sdf.format(new Date()));
					System.out.println(dateObj1);
					System.out.println(dateObj2 + "\n");
					// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00
					// GMT represented by this Date object
					long diff = dateObj2.getTime() - dateObj1.getTime();
					diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println("difference between days: " + diffDays);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else {
			diffDays = -99;
		}
	}
		return diffDays;
	}

	private String caseNumberValid(String caseNumber,int days) {
		// TODO Auto-generated method stub
		String mes = EMPTY;

		if (caseNumber == null || caseNumber.isEmpty()) {
			return ServicePartConstant.CASE_NUMBER_EMPTY;
		}
		if (caseNumber.length() > 8) {
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}
		if(caseNumber.length()<8) {
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}
		if (caseNumber.length() == 8 && days >= 60) {
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}
		if (!isAlphaNumeric(caseNumber)) {
			return ServicePartConstant.CASE_NUMBER_SPEC;
		}
		return mes;
	}

	private void driverLastNameValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getDriverLastName();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, driverLastNameValid(shipmentModel.getDriverLastName()));
	}

	private String driverLastNameValid(String driverLastName) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (driverLastName != null && driverLastName.length() > 50) {
			return ServicePartConstant.DRIVER_LAST_NAME_INVALID;
		}
		if (!isAlphaNumeric(driverLastName)) {
			return ServicePartConstant.DRIVER_LAST_NAME_SPEC;
		}

		return mes;
	}

	private void driverFirstNameValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getDriverFirstName();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, driverFirstNameValid(shipmentModel.getDriverFirstName()));
	}

	private String driverFirstNameValid(String driverFirstName) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (driverFirstName != null && driverFirstName.length() > 50) {
			return ServicePartConstant.DRIVER_FIRST_NAME_INVALID;
		}
		if (!isAlphaNumeric(driverFirstName)) {
			return ServicePartConstant.DRIVER_FIRST_NAME_SPEC;
		}

		return mes;
	}

	private void lpCodeValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getLpCode();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, lpCodeValid(shipmentModel.getLpCode()));
	}

	private String lpCodeValid(String lpCode) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (lpCode == null || lpCode.isEmpty()) {
			return ServicePartConstant.LPCODE_EMPTY;
		}
		if (lpCode != null && lpCode.length() > 6) {
			return ServicePartConstant.LPCODE_INVALID;
		}
		if (!isAlphaNumeric(lpCode)) {
			return ServicePartConstant.LPCODE_SPEC;
		}

		return mes;
	}

	private void supplierLastNameValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getSupplierLastName();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, supplierLastNameValid(shipmentModel.getSupplierLastName()));
	}

	private String supplierLastNameValid(String supplierLastName) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (supplierLastName != null && supplierLastName.length() > 50) {
			return ServicePartConstant.SUPPLIER_LAST_NAME_INVALID;
		}
		if (!isAlphaNumeric(supplierLastName)) {
			return ServicePartConstant.SUPPLIER_LAST_NAME_SPEC;
		}

		return mes;
	}

	private void supplierFirstNameValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getSupplierFirstName();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, supplierFirstNameValid(shipmentModel.getSupplierFirstName()));
	}

	private String supplierFirstNameValid(String supplierFirstName) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (supplierFirstName != null && supplierFirstName.length() > 50) {
			return ServicePartConstant.SUPPLIER_FIRST_NAME_INVALID;
		}
		if (!isAlphaNumeric(supplierFirstName)) {
			return ServicePartConstant.SUPPLIER_FIRST_NAME_SPEC;
		}

		return mes;
	}

	private void sealNumberValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getSealNumber();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, sealNumberValid(shipmentModel.getSealNumber()));
	}

	private String sealNumberValid(String sealNumber) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (sealNumber != null && sealNumber.length() > 20) {
			return ServicePartConstant.SEAL_NUMBER_INVALID;
		}
		if (!isAlphaNumeric(sealNumber)) {
			return ServicePartConstant.SEAL_NUMBER_SPEC;
		}

		return mes;
	}

	private void planPickupDateValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getPlanPickupDate();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, planPickupDateValid(shipmentModel.getPlanPickupDate()));
	}

	private String planPickupDateValid(String planPickupDate) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (planPickupDate.trim().equals("")) {
			return mes;
		} else {
			if (planPickupDate.length() > 10) {
				return ServicePartConstant.PLAN_PICKUP_DATE_INVALID;
			}
			String dateValue[] = planPickupDate.split("-");
			if (dateValue[0].length() > 4 || dateValue[1].length() > 2 || dateValue[2].length() > 2) {
				return ServicePartConstant.PLAN_PICKUP_DATE_INVALID;
			}
		}
		return mes;
	}

	private void runValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getRun();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, runValid(shipmentModel.getRun()));
	}

	private String runValid(String run) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (run != null && run.length() > 2) {
			return ServicePartConstant.RUN_INVALID;
		}
		if (!isNumeric(run)) {
			return ServicePartConstant.RUN_SPEC;
		}

		return mes;
	}

	private void routeValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getRoute();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, routeValid(shipmentModel.getRoute()));
	}

	private String routeValid(String route) {
		// TODO Auto-generated method stub
		String mes = EMPTY;
		if (route != null && route.length() > 7) {
			return ServicePartConstant.ROUTE_INVALID;
		}
		if (!isAlphaNumeric(route)) {
			return ServicePartConstant.ROUTE_SPEC;
		}

		return mes;
	}

	private boolean isAlphaNumeric(String s) {
		// TODO Auto-generated method stub
		String pattern = "^[a-zA-Z0-9]*$";
		return s.matches(pattern);
	}

	private void trailerNumberValidation(ShipmentModel shipmentModel, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub
		String key = shipmentModel.getTrailerNumber();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, trailerNumberValid(shipmentModel.getTrailerNumber()));
	}

	private String trailerNumberValid(String trailerNumber) {
		// TODO Auto-generated method stub

		String mes = EMPTY;
		if (trailerNumber == null || trailerNumber.isEmpty()) {
			return ServicePartConstant.TRAILER_NUM_EMPTY;
		}
		if (trailerNumber != null && trailerNumber.length() > 30) {
			return ServicePartConstant.TRAILER_NUM_INVALID;
		}
		if (!isAlphaNumeric(trailerNumber)) {
			return ServicePartConstant.TRAILER_NUM_SPEC;
		}

		return mes;
	}

	private void shipmentIDValidation(VendorsModel vendor, Map<String, Message> mesMap, Message mes) {
		// TODO Auto-generated method stub

		// ;
		String key = vendor.getShipmentID();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, shipmentIDValid(vendor.getShipmentID()));

	}

	private String shipmentIDValid(String s) {

		// TODO Auto-generated method stub

		String mes = EMPTY;
		if (s == null || s.isEmpty()) {
			return ServicePartConstant.SHIPMENT_ID_EMPTY;
		}
		if (s != null && s.length() > 15) {
			return ServicePartConstant.SHIPMENT_ID_INVALID;
		}
		if (!isNumeric(s)) {
			return ServicePartConstant.SHIPMENT_ID_NUMERIC;
		}

		return mes;

	}

	public void pushMessage(Message mes, String message) {
		if (message.equals(EMPTY)) {
			return;
		}
		if (mes.getErrorMessages().contains(message)) {
			return;
		}
		mes.getErrorMessages().add(message);
	}

	public boolean isNumeric(String i) {
		String pat = "^[0-9]*$";
		return i.matches(pat);
	}
}
