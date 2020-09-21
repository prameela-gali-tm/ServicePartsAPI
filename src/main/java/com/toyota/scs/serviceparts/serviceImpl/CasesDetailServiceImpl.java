package com.toyota.scs.serviceparts.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.entity.PartTransEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.CaseModel;
import com.toyota.scs.serviceparts.model.ExceptionsModel;
import com.toyota.scs.serviceparts.model.Message;
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.ResponseCaseBuildModel;
import com.toyota.scs.serviceparts.model.ResponseCaseModel;
import com.toyota.scs.serviceparts.model.ResponseUnitsModel;
import com.toyota.scs.serviceparts.model.RfidDetailsModel;
import com.toyota.scs.serviceparts.model.UnitsModel;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.repository.PartTransRepositroy;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.service.CasesDetailService;
import com.toyota.scs.serviceparts.util.DateUtils;
import com.toyota.scs.serviceparts.util.ServicePartConstant;
import com.toyota.scs.serviceparts.util.Validation;

@Service("CasesDetailService")
public class CasesDetailServiceImpl implements CasesDetailService {
	@Autowired
	private VendorRepositroy vendorRepositroy;

	@Autowired
	private OrderRepositroy orderRepositroy;

	@Autowired
	private PartRepository partRepositroy;

	@Autowired
	private CaseRepositroy caseRepositroy;

	@Autowired
	private PartTransRepositroy partTransRepositroy;

	@Autowired
	private PartDetailsServiceImpl partdetailsService;

	@Autowired
	EntityManagerFactory emf;

	static String EMPTY = "";
	boolean valid;
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public ModelApiResponse casesDetailsValidation(List<CaseBuildModel> caseModel, String status) {
		ModelApiResponse message = new ModelApiResponse();

		Message mes = new Message();
		Map<String, Message> mesMap = new HashMap<String, Message>();
		List<OrderEntity> orderList = new ArrayList<OrderEntity>();
		Validation validation = new Validation();
		List<PartTransEntity> partTransList = new ArrayList<PartTransEntity>();
		String vendorCode = null;
		List<String> vendorAndCaseNumber = new ArrayList<String>();
		// validation start here
		if (caseModel != null && caseModel.size() > 0) {
			for (int i = 0; i < caseModel.size(); i++) {
				valid = true;
				CaseBuildModel caseBuildModel = caseModel.get(i);
				vendorCode = caseBuildModel.getVendorCode();
				vendorCodeValiadation(caseBuildModel, mesMap, vendorRepositroy, mes);
				List<ExceptionsModel> exceptions = caseBuildModel.getExceptions();
				List<CaseModel> cases = caseBuildModel.getCases();
				if (cases != null && cases.size() > 0) {
					for (int j = 0; j < cases.size(); j++) {
						CaseModel model = cases.get(j);
						vendorAndCaseNumber.add(model.getCaseNumber());
						int days = caseNumberDaysValidation(model.getCaseNumber());
						pushMessage(vendorCode, caseNumberValid(model.getCaseNumber(), days), mesMap);
						String keyVendor = vendorCode + model.getCaseNumber();
						List<UnitsModel> units = model.getUnits();
						List<RfidDetailsModel> rfidDetails = model.getRfidDetails();
						for (RfidDetailsModel detailsModel : rfidDetails) {
							pushMessage(vendorCode, rfIdValidation(detailsModel.getRfid()), mesMap);
						}
						for (ExceptionsModel exceptionsModel : exceptions) {
							pushMessage(vendorCode, exceptionValidation(exceptionsModel.getExceptionCode()), mesMap);
						}
						Map<String, String> duplicateValidation = new HashMap<String, String>();

						for (UnitsModel obj : units) {
							PartEntity partEntity = null;
							OrderEntity entity = null;
							List<PartDetailsModel> detailsModel = null;
							if (obj.getDeliveryDueDate() == null) {
								detailsModel = partdetailsService.findPartDetails(obj.getPartNumber(), vendorCode);
								long outStandingQuantity = 0;
								if (detailsModel != null && detailsModel.size() > 0) {
									List<PartDetailsModel> models = new ArrayList<PartDetailsModel>();
									for (PartDetailsModel partDetailsModel : detailsModel) {
										if (partDetailsModel.getOrderQuantity() >= obj.getPartQuantity()) {
											outStandingQuantity = obj.getPartQuantity()
													- partDetailsModel.getOrderQuantity();
											partDetailsModel.setOrderQuantity(obj.getPartQuantity());
											models.add(partDetailsModel);
											break;
										} else if (partDetailsModel.getOrderQuantity() < obj.getPartQuantity()) {
											partDetailsModel.setOrderQuantity(
													obj.getPartQuantity() - partDetailsModel.getOrderQuantity());
											outStandingQuantity = obj.getPartQuantity()
													- partDetailsModel.getOrderQuantity();
											obj.setPartQuantity((int) outStandingQuantity);
											models.add(partDetailsModel);
										}

									}
									if (outStandingQuantity > 0) {
										pushMessage(vendorCode,
												"No more PoNumber does not match to assign for the given quantity ",
												mesMap);
										break;
									}
									if (valid) {
										for (PartDetailsModel partDetailsModel : models) {
											PartTransEntity partTransEntity = new PartTransEntity();
											partTransEntity.setPartId(partDetailsModel.getPartId());
											partTransEntity.setSupplierTotal(partDetailsModel.getOrderQuantity());
											partTransEntity.setTransmussionDate(new Date());
											partTransEntity.setOrderId(partDetailsModel.getOrderId());
											partTransEntity.setStatus("CASE BUILD");
											partTransEntity.setModifiedBy("sreedhar");
											partTransEntity.setModifiedDate(new Date());
											partTransEntity.setCaseNumber(model.getCaseNumber());
											partTransEntity.setPartNumber(partDetailsModel.getPartNumber());
											partTransEntity.setPoLineItemNumber(partDetailsModel.getLineItemNumber());
											partTransEntity.setDeliveryDueDate(
													DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()));
											partTransEntity.setPoNumber(partDetailsModel.getPoNumber());
											partTransList.add(partTransEntity);
										}
									}

								}
							} else {

								pushMessage(vendorCode, partNumberValidation(obj.getPartNumber()), mesMap);// part
																											// number
								pushMessage(vendorCode, poNumberValidation(obj.getPoNumber()), mesMap);// po number
								pushMessage(vendorCode, poLineNumberValidation(obj.getPoLineNumber()), mesMap);// po
																												// line
																												// item
																												// number
								pushMessage(vendorCode, partQuantityValidation(obj.getPartQuantity()), mesMap);// part
																												// quantity
								pushMessage(vendorCode, homePostionValidation(obj.getHomePosition()), mesMap);// home
																												// position
								pushMessage(vendorCode, deliverDueDateValidation(obj.getDeliveryDueDate()), mesMap);// delivery
																													// due
																													// date
								pushMessage(vendorCode, serialNumberValidation(obj.getSerialNumber()), mesMap);// serail
																												// number
								pushMessage(vendorCode, subPartNumberValidation(obj.getSubPartNumber()), mesMap);// sub
																													// part
																													// number
								entity = vendorPonumberOrderValidation(obj, vendorCode, mesMap, orderRepositroy, mes);
								partEntity = partNumberDDDLineNumbervalidation(obj, entity, mesMap, partRepositroy,
										vendorCode, duplicateValidation, model.getCaseNumber());
								if (valid) {
									PartTransEntity partTransEntity = new PartTransEntity();
									partTransEntity.setPartId(partEntity.getPartId());
									partTransEntity.setSupplierTotal(obj.getPartQuantity());
									partTransEntity.setTransmussionDate(new Date());
									partTransEntity.setOrderId(entity.getOrderId());
									partTransEntity.setStatus("CASE BUILD");
									partTransEntity.setModifiedBy("sreedhar");
									partTransEntity.setModifiedDate(new Date());
									partTransEntity.setCaseNumber(model.getCaseNumber());
									partTransEntity.setPartNumber(obj.getPartNumber());
									partTransEntity.setPoLineItemNumber(obj.getPoLineNumber());
									partTransEntity.setDeliveryDueDate(obj.getDeliveryDueDate());
									partTransEntity.setPoNumber(obj.getPoNumber());
									partTransList.add(partTransEntity);
								}
							}
						}

					}
				}

			}
		}
		// validation ends here

		if (valid) {
			String confirmationNumber = confirmationNumber(vendorCode, "C");
			message.setConfirmationNumber(confirmationNumber);
			CaseEntity caseEntity = null;
			List<CaseEntity> saveCaseList = new ArrayList<CaseEntity>();
			for (String caseNumbervalue : vendorAndCaseNumber) {
				caseEntity = new CaseEntity();
				CaseEntity caseEntityDB = caseRepositroy.findByCaseNumber(caseNumbervalue);
				if (caseEntityDB != null) {
					caseEntity = caseEntityDB;
				}
				caseEntity.setCaseNumber(caseNumbervalue);
				caseEntity.setConfirmationNumber(confirmationNumber);
				caseEntity.setStatus("CASE BUILD");
				caseEntity.setModifiedBy("sreedhar");
				caseEntity.setModifiedDate(new Date());
				saveCaseList.add(caseEntity);
			}
			caseRepositroy.saveAll(saveCaseList);
			for (PartTransEntity entry : partTransList) {
				PartTransEntity obj = entry;
				caseEntity = caseRepositroy.findByCaseNumber(obj.getCaseNumber());
				PartEntity partEntity = partRepositroy.findByPartNumberAndLineItemNumberAndDeliveryDueDate(
						entry.getPartNumber(), entry.getPoLineItemNumber(), parseDate(entry.getDeliveryDueDate()));
				PartTransEntity partTransEntity = null;
				if (caseEntity != null && partEntity != null) {
					partTransEntity = partTransRepositroy.findByCaseIdAndPartId(caseEntity.getCaseId(),
							partEntity.getPartId());
				}
				if (partTransEntity != null && partTransEntity.getCaseId() == caseEntity.getCaseId()
						&& partTransEntity.getPartId() == partEntity.getPartId()
						&& partTransEntity.getOrderId() == partEntity.getOrderId()) {
					obj.setPartTransId(partTransEntity.getPartTransId());
					obj.setCaseId(caseEntity.getCaseId());
					if (obj.getSupplierTotal() > partTransEntity.getSupplierTotal()) {
						long differenceAmount = obj.getSupplierTotal() - partTransEntity.getSupplierTotal();
						obj.setSupplierTotal(partTransEntity.getSupplierTotal() + differenceAmount);
						partEntity.setOutstandingQuantity(partEntity.getOutstandingQuantity() - differenceAmount);
					} else {
						long differenceAmount = partTransEntity.getSupplierTotal() - obj.getSupplierTotal();
						obj.setSupplierTotal(partTransEntity.getSupplierTotal() - differenceAmount);
						partEntity.setOutstandingQuantity(partEntity.getOutstandingQuantity() + differenceAmount);
					}
				} else {
					long plannedQuantity = partEntity.getOrderQuantity();
					long actaulShippedQuantity = obj.getSupplierTotal();
					long outStandingQuantity = partEntity.getOutstandingQuantity();
					long blanceQuantity = 0;
					if (outStandingQuantity == 0) {
						blanceQuantity = plannedQuantity - actaulShippedQuantity;
					} else {
						blanceQuantity = outStandingQuantity - actaulShippedQuantity;
					}
					partEntity.setOutstandingQuantity(blanceQuantity);
					partEntity.setTransmissionDate(new Date());
					partEntity.setModifiedBy("sreedhar");
					partEntity.setModifiedDate(new Date());
					obj.setCaseId(caseEntity.getCaseId());
				}
				partRepositroy.save(partEntity);
				partTransRepositroy.save(obj);
			}
		}

		if (valid) {
			message.setMessages(null);
		} else {
			message.setMessages(new ArrayList<Message>(mesMap.values()));
		}
		return message;
	}

	@Override
	public List<PartEntity> findPoNumberListItemDDD(String partNumber, String lineItem, Date deliverDuoDate,
			long orderid) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(
				"Select p from PartEntity p where p.partNumber=?1 and p.lineItemNumber=?2 and p.deliveryDueDate=?3 and p.orderId=?4");
		Query query = em.createQuery(sqlQuery.toString()).setParameter(1, partNumber).setParameter(2, lineItem)
				.setParameter(3, deliverDuoDate).setParameter(4, orderid);
		@SuppressWarnings("unchecked")
		List list = query.getResultList();
		List<PartEntity> partDetilsList = new ArrayList<PartEntity>();
		em.close();
		for (int i = 0; i < list.size(); i++) {
			Object[] list2 = (Object[]) ((Object) list.get(i));
			if (list2 != null && list2.length > 0) {
				PartEntity partEntity = (PartEntity) list2[0];
				OrderEntity orderEntity = (OrderEntity) list2[1];
				partEntity.setVendorCode(orderEntity.getVendorCode());
				partEntity.setOrderType(orderEntity.getOrderType());
				partEntity.setPoNumber(orderEntity.getPoNumber());
				partDetilsList.add(partEntity);
			}
		}
		return partDetilsList;
	}

	public String caseNumberValid(String s, int days) {
		String mes = EMPTY;
		if (s == null || s.isEmpty()) {
			return ServicePartConstant.VENDOR_CODE_EMPTY;
		}
		if (s.length() > 8) {
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}
		if (s.length() != 8 && days <= 60) {
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}
		if (!isNumeric(s)) {
			return ServicePartConstant.CASE_NUMBER_NUMBERIC;
		}
		return mes;
	}

	public String partNumberValidation(String s) {
		String mes = EMPTY;
		if (s == null || s.isEmpty()) {
			return ServicePartConstant.PART_NUMBER_EMPTY;
		}
		if (s.length() > 20) {
			return ServicePartConstant.PART_NUMBER_INVALID;
		}
		if (!isAlphaNumeric(s)) {
			return ServicePartConstant.PART_NUMBER_SPEC;
		}
		return mes;
	}

	public String poNumberValidation(String s) {
		String mes = EMPTY;
		if (s == null || s.isEmpty()) {
			return ServicePartConstant.PO_NUMBER_EMPTY;
		}
		if (s.length() > 8) {
			return ServicePartConstant.PO_NUMBER_INVALID;
		}
		if (!isAlphaNumeric(s)) {
			return ServicePartConstant.PO_NUMBER_SPEC;
		}
		return mes;
	}

	public String poLineNumberValidation(String s) {
		String mes = EMPTY;
		if (s == null || s.isEmpty()) {
			return ServicePartConstant.PO_LINE_NUMBER_EMPTY;
		}
		if (s.length() > 5) {
			return ServicePartConstant.PO_LINE_NUMBER_INVALID;
		}
		if (!isAlphaNumeric(s)) {
			return ServicePartConstant.PO_LINE_NUMBER_SPEC;
		}
		return mes;
	}

	public String partQuantityValidation(int s) {
		String mes = EMPTY;
		String value = String.valueOf(s);
		/*
		 * if(CommonisNumeric(value)) { return
		 * ServicePartConstant.PART_QUANTITY_INVALID; }
		 */
		if (value.length() > 5) {
			return ServicePartConstant.PART_QUANTITY_INVALID;
		}
		if (s == 0) {
			return ServicePartConstant.PART_QUANTITY_EMPTY;
		}

		return mes;
	}

	public String homePostionValidation(String s) {
		String mes = EMPTY;
		if (s != null && !s.equalsIgnoreCase("")) {
			if (s.length() > 1) {
				return ServicePartConstant.HOMEPOSITION_INVALID;
			}
			if (!isAlphaNumeric(s)) {
				return ServicePartConstant.HOMEPOSTION_SPEC;
			}
		}
		return mes;
	}

	public String serialNumberValidation(String s) {
		String mes = EMPTY;
		if (s != null && !s.equalsIgnoreCase("")) {
			if (s.length() > 20) {
				return ServicePartConstant.SERIAL_NUMBER_INVALID;
			}
			if (!isAlphaNumeric(s)) {
				return ServicePartConstant.SERIAL_NUMBWE_SPEC;
			}
		}
		return mes;
	}

	public String subPartNumberValidation(String s) {
		String mes = EMPTY;
		if (s != null && !s.equalsIgnoreCase("")) {
			if (s.length() > 20) {
				return ServicePartConstant.SUB_PARTNUMBER_INVALID;
			}
			if (!isAlphaNumeric(s)) {
				return ServicePartConstant.SUB_PARTNUMBER_SPEC;
			}
		}

		return mes;
	}

	public String deliverDueDateValidation(String s) {
		String mes = EMPTY;
		if (s.trim().equals("")) {
			return mes;
		} else {
			if (s.length() > 10) {
				return ServicePartConstant.DELIVERY_DUEDATE_INVALID;
			}
			String dateValue[] = s.split("-");
			if (dateValue[0].length() > 4 || dateValue[1].length() > 2 || dateValue[2].length() > 2) {
				return ServicePartConstant.DELIVERY_DUEDATE_INVALID;
			}
		}
		return mes;
	}

	public String rfIdValidation(String s) {
		String mes = EMPTY;
		if (s != null && !s.equalsIgnoreCase("")) {
			if (s.length() > 12) {
				return ServicePartConstant.RFID_INVALID;
			}
			if (!isAlphaNumeric(s)) {
				return ServicePartConstant.RFID_SPC;
			}
		}
		return mes;
	}

	public String exceptionValidation(String s) {
		String mes = EMPTY;
		if (s != null && !s.equalsIgnoreCase("")) {
			if (!isAlphaNumeric(s)) {
				return ServicePartConstant.EXCEPTION_CODE;
			}
		}
		return mes;
	}

	public String vendorCodeValid(String s, VendorRepositroy vendorRepositroy) {
		String mes = EMPTY;
		if (s == null || s.isEmpty()) {
			return ServicePartConstant.VENDOR_CODE_EMPTY;
		}
		if (s != null && s.length() > 5) {
			return ServicePartConstant.VENDOR_CODE_INVALID;
		}
		if (!isAlphaNumeric(s)) {
			return ServicePartConstant.VENDOR_CODE_SPECIAL_CHAR;
		}
		VendorEntity entity = vendorRepositroy.findByVendorCodeEquals(s);
		if (entity == null) {
			return ServicePartConstant.VENDOR_CODE_DOES_NOT_EXIST;
		}
		return mes;
	}

	public String confirmationNumber(String vendorCode, String type) {
		String cm = "";
		cm += vendorCode;
		cm += DateUtils.convertfromDateToStringFmt(new java.util.Date(), "yyMMddHHmmssss");
		cm += type;
		return cm;

	}

	public void vendorCodeValiadation(CaseBuildModel buildModel, Map<String, Message> mesMap,
			VendorRepositroy vendorRepositroy, Message mes) {
		// ;
		String key = buildModel.getVendorCode();
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, vendorCodeValid(buildModel.getVendorCode(), vendorRepositroy));
	}

	public OrderEntity vendorPonumberOrderValidation(UnitsModel unitsModel, String vendorCode,
			Map<String, Message> mesMap, OrderRepositroy orderRepositroy, Message mes) {
		// Message mes ;
		String key = vendorCode;
		OrderEntity entity = new OrderEntity();
		if (vendorCode != null && unitsModel != null) {
			entity = orderRepositroy.findByPoNumberAndVendorCode(unitsModel.getPoNumber(), vendorCode);
			if (entity == null) {
				if (!mesMap.containsKey(key)) {
					mes = new Message();
					mes.setKeyObject(key);
				} else {
					mes = mesMap.get(key);
				}

				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.ORDER_NOT_FOUND);
			}
		}
		return entity;
	}

	public PartEntity partNumberDDDLineNumbervalidation(UnitsModel obj, OrderEntity entity, Map<String, Message> mesMap,
			PartRepository partRepositroy, String vendorCode, Map<String, String> duplicateValidation,
			String caseNumber) {
		Message mes;
		String key = vendorCode;
		PartEntity partEntity = new PartEntity();
		String duplicateKey = obj.getPartNumber() + obj.getPoNumber() + obj.getPoLineNumber()
				+ obj.getDeliveryDueDate();
		if (vendorCode != null && obj != null) {
			partEntity = partRepositroy.findByPartNumberAndLineItemNumberAndDeliveryDueDate(obj.getPartNumber(),
					obj.getPoLineNumber(), parseDate(obj.getDeliveryDueDate()));
			if (!mesMap.containsKey(key)) {
				mes = new Message();
				mes.setKeyObject(key);
			} else {
				mes = mesMap.get(key);
			}
			if (duplicateValidation.containsKey(duplicateKey)) {
				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.DUPLICATE_UNITS + " " + caseNumber);
			} else {
				duplicateValidation.put(duplicateKey, duplicateKey);
			}
			if (partEntity != null && entity != null) {
				if (partEntity.getOrderId() != entity.getOrderId()) {
					mesMap.put(key, mes);
					pushMessage(mes, ServicePartConstant.PART_LINE_DDD);
				}
				if (partEntity.getOutstandingQuantity() == 0) {
					if (obj.getPartQuantity() > partEntity.getOrderQuantity()) {
						mesMap.put(key, mes);
						pushMessage(mes,
								ServicePartConstant.ORDER_QUANTITY + " for the part numner " + obj.getPartNumber()
										+ " and PoNumber " + obj.getPoNumber() + " and line item number "
										+ obj.getPoLineNumber());
					}
				} else {
					if (obj.getPartQuantity() > partEntity.getOutstandingQuantity()) {
						mesMap.put(key, mes);
						pushMessage(mes,
								ServicePartConstant.ORDER_QUANTITY + " for the part numner " + obj.getPartNumber()
										+ " and PoNumber " + obj.getPoNumber() + " and line item number "
										+ obj.getPoLineNumber());
					}
				}
			} else {
				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.PART_LINE_DDD_INVALID);
			}

		}
		return partEntity;

	}

	public void pushMessage(String key, String message, Map<String, Message> mesMap) {
		if (message.equals(EMPTY)) {
			return;
		}
		Message mes;
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		pushMessage(mes, message);
		mesMap.put(key, mes);
	}

	public void pushMessage(Message mes, String message) {
		if (message.equals(EMPTY)) {
			return;
		}
		valid = false;
		if (mes.getErrorMessages().contains(message)) {
			return;
		}
		mes.getErrorMessages().add(message);
	}

	public void validateCaseBulid(CaseBuildModel buildModel, Map<String, Message> mesMap) {

		// pushMessage(mes, caseNumberValid(buildModel.getCases().ge));
	}

	public java.sql.Date parseDate(String date) {
		try {
			date = date.replace("T", " ");
			return new java.sql.Date(DATE_FORMAT.parse(date).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public java.sql.Timestamp parseTimestamp(String timestamp) {
		try {
			timestamp = timestamp.replace("T", " ");
			return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public boolean isAlphaNumeric(String s) {
		String pattern = "^[a-zA-Z0-9]*$";
		return s.matches(pattern);
	}

	public boolean isNumeric(String i) {
		String pat = "^[0-9]*$";
		return i.matches(pat);
	}

	@Override
	public int caseNumberDaysValidation(String caseNumber) {
		CaseEntity caseEntity = caseRepositroy.findByCaseNumber(caseNumber);
		int diffDays = 0;
		if (caseEntity != null) {
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
		}
		return diffDays;
	}

	public long findDifference(String start_date, String end_date) {
		long days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			Date d1 = sdf.parse(start_date);
			Date d2 = sdf.parse(end_date);
			long difference_In_Time = d2.getTime() - d1.getTime();
			days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		}
		// Catch the Exception
		catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	@Override
	public ModelApiResponse casesBuildVendorAndPartNumber(List<CaseBuildModel> caseModel, String status) {
		ModelApiResponse message = new ModelApiResponse();
		Message mes = new Message();
		Map<String, Message> mesMap = new HashMap<String, Message>();
		String vendorCode = null;
		if (caseModel != null && caseModel.size() > 0) {
			Map<String, Long> partDetailsMap = new HashMap<String, Long>();//// part number with reaming quantity
			Map<String, List<PartDetailsModel>> caseWithUnitDetails = new HashMap<String, List<PartDetailsModel>>();
			for (int i = 0; i < caseModel.size(); i++) {
				valid = true;
				CaseBuildModel caseBuildModel = caseModel.get(i);
				if (i == 0) {
					vendorCode = caseBuildModel.getVendorCode();
				}
				List<CaseModel> cases = caseBuildModel.getCases();
				if (cases != null && cases.size() > 0) {
					Map<String, Map<String, PartDetailsModel>> partWithCompleteDDD = new HashMap<String, Map<String, PartDetailsModel>>();
					Map<String, PartDetailsModel> dddCompleteRecords = new HashMap<String, PartDetailsModel>();

					for (int j = 0; j < cases.size(); j++) {
						List<PartDetailsModel> unitDetails = new ArrayList<PartDetailsModel>();
						CaseModel model = cases.get(j);
						int days = caseNumberDaysValidation(model.getCaseNumber());
						pushMessage(vendorCode, caseNumberValid(model.getCaseNumber(), days), mesMap);
						List<UnitsModel> units = model.getUnits();
						List<RfidDetailsModel> rfidDetails = model.getRfidDetails();
						List<ExceptionsModel> exceptions = caseBuildModel.getExceptions();
						for (RfidDetailsModel detailsModel : rfidDetails) {
							pushMessage(vendorCode, rfIdValidation(detailsModel.getRfid()), mesMap);
						}
						for (ExceptionsModel exceptionsModel : exceptions) {
							pushMessage(vendorCode, exceptionValidation(exceptionsModel.getExceptionCode()), mesMap);
						}

						for (UnitsModel obj : units) {
							List<PartDetailsModel> detailsModel = null;
							String keyValue = obj.getPartNumber() + vendorCode;
							long outStandingQuantity = 0;
							if (obj.getDeliveryDueDate() == null) {
								detailsModel = partdetailsService.findPartDetails(obj.getPartNumber(), vendorCode);
								if (partDetailsMap.containsKey(keyValue)) {
									outStandingQuantity = partDetailsMap.get(keyValue);
								}
								if (detailsModel != null && detailsModel.size() > 0) {
									for (PartDetailsModel partDetailsModel : detailsModel) {
										boolean falgIteration = false;
										if (partWithCompleteDDD.containsKey(partDetailsModel.getPartNumber())) {
											Map<String, PartDetailsModel> dddValues = partWithCompleteDDD
													.get(partDetailsModel.getPartNumber());
											if (dddValues.containsKey(
													DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()))) {
												PartDetailsModel detailsModel1 = dddValues
														.get(DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()));
												if (detailsModel1 != null && detailsModel1.getPartialStatus()
														.equalsIgnoreCase("PARTIAL")) {
													falgIteration = true;
												}
												partDetailsModel.setOrderQuantity(detailsModel1.getOrderQuantity());
												partDetailsModel
														.setOutstandingQuantity(detailsModel1.getOutstandingQuantity());
												partDetailsModel.setSupplierFullFillQuantity(
														detailsModel1.getSupplierFullFillQuantity());
												partDetailsModel.setPartialStatus(detailsModel1.getPartialStatus());
											}
										}
										long plannedQuantity = partDetailsModel.getOrderQuantity();
										long actaulShippedQuantity = obj.getPartQuantity();
										long fullfillQuantity = partDetailsModel.getSupplierFullFillQuantity();
										long remainingQuantity = partDetailsModel.getOutstandingQuantity();
										String partialStatus = partDetailsModel.getPartialStatus();
										long balanceQuantity = 0;
										if (fullfillQuantity == 0) {
											if (actaulShippedQuantity >= plannedQuantity) {
												partDetailsModel.setSupplierFullFillQuantity(plannedQuantity);
												partDetailsModel.setOutstandingQuantity(0);
												partDetailsModel.setPartialStatus("FULL FILLED");
												balanceQuantity = actaulShippedQuantity - plannedQuantity;
												dddCompleteRecords.put(
														DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()),
														partDetailsModel);
												unitDetails.add(partDetailsModel);
												if (balanceQuantity <= 0) {
													obj.setPartQuantity(0);
													outStandingQuantity = 0;
													break;
												} else {
													obj.setPartQuantity((int) balanceQuantity);
													outStandingQuantity = balanceQuantity;
												}
											} else {
												balanceQuantity = actaulShippedQuantity - plannedQuantity;
												partDetailsModel.setSupplierFullFillQuantity(actaulShippedQuantity);
												partDetailsModel.setOutstandingQuantity(
														(plannedQuantity - actaulShippedQuantity));
												partDetailsModel.setPartialStatus("PARTIAL");
												dddCompleteRecords.put(
														DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()),
														partDetailsModel);
												unitDetails.add(partDetailsModel);
												if (balanceQuantity <= 0) {
													obj.setPartQuantity(0);
													outStandingQuantity = 0;
													break;
												} else {
													obj.setPartQuantity((int) balanceQuantity);
													outStandingQuantity = balanceQuantity;
												}

											}
										} else if (partialStatus != null && partialStatus.equalsIgnoreCase("PARTIAL")) {
											if (remainingQuantity != 0) {
												if (actaulShippedQuantity >= remainingQuantity) {

													partDetailsModel.setSupplierFullFillQuantity(
															fullfillQuantity + remainingQuantity);
													partDetailsModel.setOutstandingQuantity(0);
													partDetailsModel.setPartialStatus("FULL FILLED");
													balanceQuantity = actaulShippedQuantity - remainingQuantity;
													dddCompleteRecords.put(
															DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()),
															partDetailsModel);
													if (falgIteration) {
														partDetailsModel.setSupplierFullFillQuantity(remainingQuantity);
													}
													unitDetails.add(partDetailsModel);
													falgIteration = false;
													if (balanceQuantity <= 0) {
														obj.setPartQuantity(0);
														outStandingQuantity = 0;
														break;
													} else {
														obj.setPartQuantity((int) balanceQuantity);
														outStandingQuantity = balanceQuantity;
													}
												}
											}
										}

										if (!falgIteration) {
											if (actaulShippedQuantity > 0) {
												outStandingQuantity = actaulShippedQuantity;
											}

										}
									} // end of the for loop for the iteration of the DB details

								} // end of the if condition for the details fetching from the data base
								partWithCompleteDDD.put(obj.getPartNumber(), dddCompleteRecords);
							} // end of the if condition for the delivery due date end
							partDetailsMap.put(keyValue, outStandingQuantity);
						} /// end of the for loop unit level
						caseWithUnitDetails.put(model.getCaseNumber(), unitDetails);
					}

				}
			}
			for (Map.Entry mapElement : partDetailsMap.entrySet()) {
				String key = (String) mapElement.getKey();
				long value = (long) mapElement.getValue();
				if (value > 0) {
					pushMessage(vendorCode, "No more PoNumber does not match to assign for the given quantity " + key
							+ "Reamining Values ------>" + value, mesMap);
					valid = false;
				}
			}

			/*
			 * if (caseWithUnitDetails != null && caseWithUnitDetails.size() > 0) {
			 * TreeMap<String, List<PartDetailsModel>> sorting = new TreeMap<String,
			 * List<PartDetailsModel>>(); sorting.putAll(caseWithUnitDetails); for
			 * (Map.Entry<String, List<PartDetailsModel>> objects : sorting.entrySet()) {
			 * System.out.println(
			 * "--------------------------------------------------------------------------")
			 * ; System.out.
			 * println("-------------------------------case number------------------------------->"
			 * ); System.out.println("Case Number---------------------------" +
			 * objects.getKey()); System.out.
			 * println("--------------------------------Units details-------------------------"
			 * ); for (PartDetailsModel detailsModel : objects.getValue()) {
			 * System.out.println("Part Number-----------------------" +
			 * detailsModel.getPartNumber());
			 * System.out.println("PO NUmber-------------------------" +
			 * detailsModel.getPoNumber());
			 * System.out.println("Delivery due date ----------------" +
			 * detailsModel.getDeliveryDueDate());
			 * System.out.println("Planned Quantity in DB------------" +
			 * detailsModel.getOrderQuantity()); System.out
			 * .println("Outstading quantity---------------" +
			 * detailsModel.getOutstandingQuantity()); System.out.println(
			 * "FullFillment quantity-------------" +
			 * detailsModel.getSupplierFullFillQuantity());
			 * System.out.println("Staus-----------------------------" +
			 * detailsModel.getPartialStatus()); } System.out.
			 * println("----------------End of Case Number details --------------------------"
			 * ); } }
			 */
			
			/// Saving the Records into Data base start here
			//valid =false;/// need to remove after demo
			if(valid) {
				String confirmationNumber = confirmationNumber(vendorCode, "C");
				message.setConfirmationNumber(confirmationNumber);
				if (caseWithUnitDetails != null && caseWithUnitDetails.size() > 0) {
					TreeMap<String, List<PartDetailsModel>> sorting = new TreeMap<String, List<PartDetailsModel>>();
					sorting.putAll(caseWithUnitDetails);
					CaseEntity caseEntity = null;
					List<CaseEntity> saveCaseList = new ArrayList<CaseEntity>();
					for (Map.Entry<String, List<PartDetailsModel>> objects : sorting.entrySet()) {
						caseEntity = new CaseEntity();
						CaseEntity caseEntityDB = caseRepositroy.findByCaseNumber(objects.getKey());
						if (caseEntityDB != null) {
							caseEntity = caseEntityDB;
						}
						caseEntity.setCaseNumber(objects.getKey());
						caseEntity.setConfirmationNumber(confirmationNumber);
						caseEntity.setStatus("CASE BUILD");
						caseEntity.setModifiedBy("sreedhar");
						caseEntity.setModifiedDate(new Date());
						saveCaseList.add(caseEntity);
					}
					caseRepositroy.saveAll(saveCaseList);
					if(saveCaseList!=null && saveCaseList.size()>0) {
						List<PartTransEntity> partTransList = new ArrayList<PartTransEntity>();
						List<PartEntity> partList = new ArrayList<PartEntity>();
						ResponseCaseBuildModel responseCaseBuildModel = new ResponseCaseBuildModel();
						responseCaseBuildModel.setVendorCode(vendorCode);
						List<ResponseCaseModel> responseCaseModelsList = new ArrayList<ResponseCaseModel>();
						for(CaseEntity obj:saveCaseList) {
							ResponseCaseModel responseCaseModel = new ResponseCaseModel();
							responseCaseModel.setCaseNumber(obj.getCaseNumber());
							List<ResponseUnitsModel> responseUnitsModelsList = new ArrayList<ResponseUnitsModel>();
							List<PartDetailsModel> savePartDetails = caseWithUnitDetails.get(obj.getCaseNumber());
							for(PartDetailsModel partDetailsModel:savePartDetails) {
								/// saving the part transaction details start here
								PartTransEntity partTransEntity = new PartTransEntity();
								partTransEntity.setCaseId(obj.getCaseId());
								partTransEntity.setFullfilledQuantity(partDetailsModel.getSupplierFullFillQuantity());
								partTransEntity.setModifiedBy("SYSTEM");
								partTransEntity.setModifiedDate(new Date());
								partTransEntity.setOrderId(partDetailsModel.getOrderId());
								partTransEntity.setPartId(partDetailsModel.getPartId());
								partTransEntity.setSerialNumber(null);
								partTransEntity.setStatus(partDetailsModel.getPartialStatus());
								partTransEntity.setSupplierTotal(partDetailsModel.getSupplierFullFillQuantity());
								partTransEntity.setTransmussionDate(new Date());
								partTransList.add(partTransEntity);
								
								// Ends here
								
								/// Part details updating start here
								PartEntity partEntity = new  PartEntity();
								partEntity.setPartId(partDetailsModel.getPartId());
								partEntity.setContainerID(partDetailsModel.getContainerID());
								partEntity.setDealer(partDetailsModel.getDealer());
								partEntity.setDeliveryDueDate(partDetailsModel.getDeliveryDueDate());
								partEntity.setDirectShip(partDetailsModel.getDirectShip());
								partEntity.setHomePosition(partDetailsModel.getHomePosition());
								partEntity.setLineItemNumber(partDetailsModel.getLineItemNumber());
								partEntity.setModifiedBy("SYSTEM");
								partEntity.setModifiedDate(new Date());
								partEntity.setOrderId(partDetailsModel.getOrderId());
								partEntity.setOrderQuantity(partDetailsModel.getOrderQuantity());
								partEntity.setOrderRefNumber(partDetailsModel.getOrderRefNumber());								
								partEntity.setOutstandingQuantity(partDetailsModel.getOutstandingQuantity());
								partEntity.setPartDesc(partDetailsModel.getPartDesc());
								partEntity.setPartNumber(partDetailsModel.getPartNumber());
								partEntity.setSerialNumber(partDetailsModel.getSerialNumber());
								partEntity.setStatus(partDetailsModel.getPartialStatus());
								partEntity.setSubPartNumber(partDetailsModel.getSubPartNumber());
								partEntity.setTransmissionDate(new Date());
								partEntity.setVendorPartNumber(partDetailsModel.getVendorPartNumber());
								partList.add(partEntity);
								// ends here
								/// Response object building start here
								ResponseUnitsModel responseUnitsModel = new  ResponseUnitsModel();
								responseUnitsModel.setPartNumber(partDetailsModel.getPartNumber());
								responseUnitsModel.setPoNumber(partDetailsModel.getPoNumber());
								responseUnitsModel.setPoLineNumber(partDetailsModel.getLineItemNumber());
								responseUnitsModel.setHomePosition(partDetailsModel.getHomePosition());
								responseUnitsModel.setDeliveryDueDate(DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()));
								responseUnitsModel.setPlannedOrderQuantity((int)partDetailsModel.getOrderQuantity());
								responseUnitsModel.setOutstandingOrderQuantity(partDetailsModel.getOutstandingQuantity());
								responseUnitsModel.setSupplierOrderQuantityFullFilled(partDetailsModel.getSupplierFullFillQuantity());
								responseUnitsModel.setOrderStatus(partDetailsModel.getPartialStatus());
								responseUnitsModelsList.add(responseUnitsModel);
								/// ends here
							}
							responseCaseModel.setUnits(responseUnitsModelsList);
							responseCaseModelsList.add(responseCaseModel);
						}
						responseCaseBuildModel.setCases(responseCaseModelsList);
						message.setResponseCaseBuildDetails(responseCaseBuildModel);
						partRepositroy.saveAll(partList);
						partTransRepositroy.saveAll(partTransList);
					}
					
				}
			}
			//Ends here
			
		}

		if (valid) {
			message.setMessages(null);
		} else {
			message.setMessages(new ArrayList<Message>(mesMap.values()));
		}
		return message;
	}

}
