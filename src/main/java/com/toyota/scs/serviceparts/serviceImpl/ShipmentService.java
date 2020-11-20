package com.toyota.scs.serviceparts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.entity.RoutePathNodeEntity;
import com.toyota.scs.serviceparts.entity.ShipmentEntity;
import com.toyota.scs.serviceparts.model.CaseModel;
import com.toyota.scs.serviceparts.model.ShipmentModel;
import com.toyota.scs.serviceparts.model.VendorsModel;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.repository.RoutePathNodeRepositroy;
import com.toyota.scs.serviceparts.repository.ShipmentRepositroy;

@Service
public class ShipmentService {

	@Autowired
	ShipmentRepositroy shipmentRepositroy;
	@Autowired
	CaseRepositroy caseRepositroy;
	@Autowired
	RoutePathNodeRepositroy routePathNodeRepository;

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

	public ShipmentEntity saveShipmentDetails(ShipmentModel shipmentModel) throws ParseException {
		// TODO Auto-generated method stub

		ShipmentEntity shipEntity = new ShipmentEntity();
		List<RoutePathNodeEntity> routepathNode = new ArrayList<RoutePathNodeEntity>();

		if (shipmentModel.getTrailerNumber() != null && shipmentModel.getTrailerNumber() != "") {
			shipEntity.setTrailer(shipmentModel.getTrailerNumber());
		}
		if (shipmentModel.getRoute() != null && shipmentModel.getRoute() != "") {
			shipEntity.setRoute(shipmentModel.getRoute());
		}
		if (shipmentModel.getRun() != null && shipmentModel.getRun() != "") {
			shipEntity.setRun(Long.parseLong(shipmentModel.getRun()));
		}
		if (shipmentModel.getPlanPickupDate() != null && shipmentModel.getPlanPickupDate() != "") {

			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date modifiedDate = formatter2.parse(shipmentModel.getPlanPickupDate());
			shipEntity.setModifiedDate(modifiedDate);
		}

		if (shipmentModel.getSealNumber() != null && shipmentModel.getSealNumber() != "") {
			shipEntity.setTrackingNumber(shipmentModel.getSealNumber());
		}
		if (shipmentModel.getSupplierFirstName() != null && shipmentModel.getSupplierFirstName() != "") {
			shipEntity.setSupplierFirstName(shipmentModel.getSupplierFirstName());
		}
		if (shipmentModel.getSupplierLastName() != null && shipmentModel.getSupplierLastName() != "") {
			shipEntity.setSupplierLastName(shipmentModel.getSupplierLastName());
		}
		if (shipmentModel.getLpCode() != null && shipmentModel.getLpCode() != "") {
			shipEntity.setScacCode(shipmentModel.getLpCode());
		}
		if (shipmentModel.getDriverFirstName() != null && shipmentModel.getDriverFirstName() != "") {
			shipEntity.setDriverFirstName(shipmentModel.getDriverFirstName());
		}
		if (shipmentModel.getDriverLastName() != null && shipmentModel.getDriverLastName() != "") {
			shipEntity.setDriverLastName(shipmentModel.getDriverLastName());
		}

		ShipmentEntity shipmentEntity = shipmentRepositroy.save(shipEntity);
		if (shipmentModel != null && shipmentModel.getVendors().size() > 0) {
			for (VendorsModel vendor : shipmentModel.getVendors()) {
				if (vendor != null && vendor.getCaseModel().size() > 0) {

					for (CaseModel cases : vendor.getCaseModel()) {
						if (cases != null && cases.getCaseNumber() != null) {

							CaseEntity caseEntity = caseRepositroy.findByCaseNumber(cases.getCaseNumber());

							if (caseEntity != null) {
								// caseList.add(caseEntity.getId());
								RoutePathNodeEntity routePathNodeEntity = new RoutePathNodeEntity();
								routePathNodeEntity.setCaseId(caseEntity.getId());
								routePathNodeEntity.setShipmentId(Long.parseLong(vendor.getShipmentID()));
								routePathNodeEntity.setRoutePathId(shipmentEntity.getId());

								routepathNode.add(routePathNodeEntity);
							} else {
								// case number doesn't exist
							}
						}

					}

				}
			}
			routePathNodeRepository.saveAll(routepathNode);

		}
		return shipmentEntity;
	}
}
