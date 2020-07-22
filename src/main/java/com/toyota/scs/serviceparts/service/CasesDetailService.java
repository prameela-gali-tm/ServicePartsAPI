package com.toyota.scs.serviceparts.service;

import java.util.List;

import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.ModelApiResponse;

public interface CasesDetailService {

	public ModelApiResponse casesDetailsValidation(List<CaseBuildModel> caseModel,String status);
}
