package com.toyota.scs.serviceparts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolineModel {

	    @JsonProperty("EDI_PRTNR_ID") 
	    public String eDI_PRTNR_ID;
	    @JsonProperty("SCS_IND") 
	    public String sCS_IND;
	    @JsonProperty("VDR_CD") 
	    public String vDR_CD;
	    @JsonProperty("SUPPLR_CD") 
	    public String sUPPLR_CD;
	    @JsonProperty("PO_NUM") 
	    public String pO_NUM;
	    @JsonProperty("ORD_TYP") 
	    public String oRD_TYP;
	    @JsonProperty("TRANSP_CD") 
	    public String tRANSP_CD;
	    @JsonProperty("LINE_ITEM_NUM") 
	    public String lINE_ITEM_NUM;
	    @JsonProperty("PART_NUM") 
	    public String pART_NUM;
	    @JsonProperty("TOYOTA_PART_NUM") 
	    public String tOYOTA_PART_NUM;
	    @JsonProperty("ORD_QTY_PER_DDD") 
	    public String oRD_QTY_PER_DDD;
	    @JsonProperty("ORIG_DDD") 
	    public String oRIG_DDD ;	    
	    @JsonProperty("REVISED_DDD") 
	    public String rEVISED_DDD;
	    
	    @JsonProperty("HP") 
	    public String hP;
	    @JsonProperty("BO_QTY") 
	    public String bO_QTY;
	    @JsonProperty("PART_DESC") 
	    public String pART_DESC;
	    @JsonProperty("ANALYST_NAME") 
	    public String aNALYST_NAME;
	    @JsonProperty("DISTFD") 
	    public String dISTFD;
	    @JsonProperty("FINAL_DST") 
	    public String fINAL_DST;
	    @JsonProperty("DLR_ORD_REF_NUM") 
	    public String dLR_ORD_REF_NUM;
	    @JsonProperty("DLR_CODE") 
	    public String dLR_CODE;
	    @JsonProperty("DIRECT_SHP_FLG") 
	    public String dIRECT_SHP_FLG;
	    @JsonProperty("VDR_FILL_LT") 
	    public String vDR_FILL_LT;
	    @JsonProperty("VDR_XSIT_LT") 
	    public String vDR_XSIT_LT;
	    @JsonProperty("EDA") 
	    public String eDA;
	    @JsonProperty("TRACKING_FLG") 
	    public String tRACKING_FLG;
	    @JsonProperty("TIME_STAMP") 
	    public String tIME_STAMP;
	  
	    public PolineModel() {
	    	super();
	    }
	    
		
		public String getoRIG_DDD() {
			return oRIG_DDD;
		}


		public void setoRIG_DDD(String oRIG_DDD) {
			this.oRIG_DDD = oRIG_DDD;
		}


		public String getrEVISED_DDD() {
			return rEVISED_DDD;
		}


		public void setrEVISED_DDD(String rEVISED_DDD) {
			this.rEVISED_DDD = rEVISED_DDD;
		}


		public String geteDI_PRTNR_ID() {
			return eDI_PRTNR_ID;
		}
		public void seteDI_PRTNR_ID(String eDI_PRTNR_ID) {
			this.eDI_PRTNR_ID = eDI_PRTNR_ID;
		}
		public String getsCS_IND() {
			return sCS_IND;
		}
		public void setsCS_IND(String sCS_IND) {
			this.sCS_IND = sCS_IND;
		}
		public String getvDR_CD() {
			return vDR_CD;
		}
		public void setvDR_CD(String vDR_CD) {
			this.vDR_CD = vDR_CD;
		}
		public String getsUPPLR_CD() {
			return sUPPLR_CD;
		}
		public void setsUPPLR_CD(String sUPPLR_CD) {
			this.sUPPLR_CD = sUPPLR_CD;
		}
		public String getpO_NUM() {
			return pO_NUM;
		}
		public void setpO_NUM(String pO_NUM) {
			this.pO_NUM = pO_NUM;
		}
		public String getoRD_TYP() {
			return oRD_TYP;
		}
		public void setoRD_TYP(String oRD_TYP) {
			this.oRD_TYP = oRD_TYP;
		}
		public String gettRANSP_CD() {
			return tRANSP_CD;
		}
		public void settRANSP_CD(String tRANSP_CD) {
			this.tRANSP_CD = tRANSP_CD;
		}
		public String getlINE_ITEM_NUM() {
			return lINE_ITEM_NUM;
		}
		public void setlINE_ITEM_NUM(String lINE_ITEM_NUM) {
			this.lINE_ITEM_NUM = lINE_ITEM_NUM;
		}
		public String getpART_NUM() {
			return pART_NUM;
		}
		public void setpART_NUM(String pART_NUM) {
			this.pART_NUM = pART_NUM;
		}
		public String gettOYOTA_PART_NUM() {
			return tOYOTA_PART_NUM;
		}
		public void settOYOTA_PART_NUM(String tOYOTA_PART_NUM) {
			this.tOYOTA_PART_NUM = tOYOTA_PART_NUM;
		}
		public String getoRD_QTY_PER_DDD() {
			return oRD_QTY_PER_DDD;
		}
		public void setoRD_QTY_PER_DDD(String oRD_QTY_PER_DDD) {
			this.oRD_QTY_PER_DDD = oRD_QTY_PER_DDD;
		}
		
		public String gethP() {
			return hP;
		}
		public void sethP(String hP) {
			this.hP = hP;
		}
		public String getbO_QTY() {
			return bO_QTY;
		}
		public void setbO_QTY(String bO_QTY) {
			this.bO_QTY = bO_QTY;
		}
		public String getpART_DESC() {
			return pART_DESC;
		}
		public void setpART_DESC(String pART_DESC) {
			this.pART_DESC = pART_DESC;
		}
		public String getaNALYST_NAME() {
			return aNALYST_NAME;
		}
		public void setaNALYST_NAME(String aNALYST_NAME) {
			this.aNALYST_NAME = aNALYST_NAME;
		}
		public String getdISTFD() {
			return dISTFD;
		}
		public void setdISTFD(String dISTFD) {
			this.dISTFD = dISTFD;
		}
		public String getfINAL_DST() {
			return fINAL_DST;
		}
		public void setfINAL_DST(String fINAL_DST) {
			this.fINAL_DST = fINAL_DST;
		}
		public String getdLR_ORD_REF_NUM() {
			return dLR_ORD_REF_NUM;
		}
		public void setdLR_ORD_REF_NUM(String dLR_ORD_REF_NUM) {
			this.dLR_ORD_REF_NUM = dLR_ORD_REF_NUM;
		}
		public String getdLR_CODE() {
			return dLR_CODE;
		}
		public void setdLR_CODE(String dLR_CODE) {
			this.dLR_CODE = dLR_CODE;
		}
		public String getdIRECT_SHP_FLG() {
			return dIRECT_SHP_FLG;
		}
		public void setdIRECT_SHP_FLG(String dIRECT_SHP_FLG) {
			this.dIRECT_SHP_FLG = dIRECT_SHP_FLG;
		}
		public String getvDR_FILL_LT() {
			return vDR_FILL_LT;
		}
		public void setvDR_FILL_LT(String vDR_FILL_LT) {
			this.vDR_FILL_LT = vDR_FILL_LT;
		}
		public String getvDR_XSIT_LT() {
			return vDR_XSIT_LT;
		}
		public void setvDR_XSIT_LT(String vDR_XSIT_LT) {
			this.vDR_XSIT_LT = vDR_XSIT_LT;
		}
		public String geteDA() {
			return eDA;
		}
		public void seteDA(String eDA) {
			this.eDA = eDA;
		}
		public String gettRACKING_FLG() {
			return tRACKING_FLG;
		}
		public void settRACKING_FLG(String tRACKING_FLG) {
			this.tRACKING_FLG = tRACKING_FLG;
		}
		public String gettIME_STAMP() {
			return tIME_STAMP;
		}
		public void settIME_STAMP(String tIME_STAMP) {
			this.tIME_STAMP = tIME_STAMP;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((aNALYST_NAME == null) ? 0 : aNALYST_NAME.hashCode());
			result = prime * result + ((bO_QTY == null) ? 0 : bO_QTY.hashCode());
			result = prime * result + ((dIRECT_SHP_FLG == null) ? 0 : dIRECT_SHP_FLG.hashCode());
			result = prime * result + ((dISTFD == null) ? 0 : dISTFD.hashCode());
			result = prime * result + ((dLR_CODE == null) ? 0 : dLR_CODE.hashCode());
			result = prime * result + ((dLR_ORD_REF_NUM == null) ? 0 : dLR_ORD_REF_NUM.hashCode());
			result = prime * result + ((eDA == null) ? 0 : eDA.hashCode());
			result = prime * result + ((eDI_PRTNR_ID == null) ? 0 : eDI_PRTNR_ID.hashCode());
			result = prime * result + ((fINAL_DST == null) ? 0 : fINAL_DST.hashCode());
			result = prime * result + ((hP == null) ? 0 : hP.hashCode());
			result = prime * result + ((lINE_ITEM_NUM == null) ? 0 : lINE_ITEM_NUM.hashCode());
			result = prime * result + ((oRD_QTY_PER_DDD == null) ? 0 : oRD_QTY_PER_DDD.hashCode());
			result = prime * result + ((oRD_TYP == null) ? 0 : oRD_TYP.hashCode());
			result = prime * result + ((oRIG_DDD == null) ? 0 : oRIG_DDD.hashCode());
			result = prime * result + ((pART_DESC == null) ? 0 : pART_DESC.hashCode());
			result = prime * result + ((pART_NUM == null) ? 0 : pART_NUM.hashCode());
			result = prime * result + ((pO_NUM == null) ? 0 : pO_NUM.hashCode());
			result = prime * result + ((rEVISED_DDD == null) ? 0 : rEVISED_DDD.hashCode());
			result = prime * result + ((sCS_IND == null) ? 0 : sCS_IND.hashCode());
			result = prime * result + ((sUPPLR_CD == null) ? 0 : sUPPLR_CD.hashCode());
			result = prime * result + ((tIME_STAMP == null) ? 0 : tIME_STAMP.hashCode());
			result = prime * result + ((tOYOTA_PART_NUM == null) ? 0 : tOYOTA_PART_NUM.hashCode());
			result = prime * result + ((tRACKING_FLG == null) ? 0 : tRACKING_FLG.hashCode());
			result = prime * result + ((tRANSP_CD == null) ? 0 : tRANSP_CD.hashCode());
			result = prime * result + ((vDR_CD == null) ? 0 : vDR_CD.hashCode());
			result = prime * result + ((vDR_FILL_LT == null) ? 0 : vDR_FILL_LT.hashCode());
			result = prime * result + ((vDR_XSIT_LT == null) ? 0 : vDR_XSIT_LT.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PolineModel other = (PolineModel) obj;
			if (aNALYST_NAME == null) {
				if (other.aNALYST_NAME != null)
					return false;
			} else if (!aNALYST_NAME.equals(other.aNALYST_NAME))
				return false;
			if (bO_QTY == null) {
				if (other.bO_QTY != null)
					return false;
			} else if (!bO_QTY.equals(other.bO_QTY))
				return false;
			if (dIRECT_SHP_FLG == null) {
				if (other.dIRECT_SHP_FLG != null)
					return false;
			} else if (!dIRECT_SHP_FLG.equals(other.dIRECT_SHP_FLG))
				return false;
			if (dISTFD == null) {
				if (other.dISTFD != null)
					return false;
			} else if (!dISTFD.equals(other.dISTFD))
				return false;
			if (dLR_CODE == null) {
				if (other.dLR_CODE != null)
					return false;
			} else if (!dLR_CODE.equals(other.dLR_CODE))
				return false;
			if (dLR_ORD_REF_NUM == null) {
				if (other.dLR_ORD_REF_NUM != null)
					return false;
			} else if (!dLR_ORD_REF_NUM.equals(other.dLR_ORD_REF_NUM))
				return false;
			if (eDA == null) {
				if (other.eDA != null)
					return false;
			} else if (!eDA.equals(other.eDA))
				return false;
			if (eDI_PRTNR_ID == null) {
				if (other.eDI_PRTNR_ID != null)
					return false;
			} else if (!eDI_PRTNR_ID.equals(other.eDI_PRTNR_ID))
				return false;
			if (fINAL_DST == null) {
				if (other.fINAL_DST != null)
					return false;
			} else if (!fINAL_DST.equals(other.fINAL_DST))
				return false;
			if (hP == null) {
				if (other.hP != null)
					return false;
			} else if (!hP.equals(other.hP))
				return false;
			if (lINE_ITEM_NUM == null) {
				if (other.lINE_ITEM_NUM != null)
					return false;
			} else if (!lINE_ITEM_NUM.equals(other.lINE_ITEM_NUM))
				return false;
			if (oRD_QTY_PER_DDD == null) {
				if (other.oRD_QTY_PER_DDD != null)
					return false;
			} else if (!oRD_QTY_PER_DDD.equals(other.oRD_QTY_PER_DDD))
				return false;
			if (oRD_TYP == null) {
				if (other.oRD_TYP != null)
					return false;
			} else if (!oRD_TYP.equals(other.oRD_TYP))
				return false;
			if (oRIG_DDD == null) {
				if (other.oRIG_DDD != null)
					return false;
			} else if (!oRIG_DDD.equals(other.oRIG_DDD))
				return false;
			if (pART_DESC == null) {
				if (other.pART_DESC != null)
					return false;
			} else if (!pART_DESC.equals(other.pART_DESC))
				return false;
			if (pART_NUM == null) {
				if (other.pART_NUM != null)
					return false;
			} else if (!pART_NUM.equals(other.pART_NUM))
				return false;
			if (pO_NUM == null) {
				if (other.pO_NUM != null)
					return false;
			} else if (!pO_NUM.equals(other.pO_NUM))
				return false;
			if (rEVISED_DDD == null) {
				if (other.rEVISED_DDD != null)
					return false;
			} else if (!rEVISED_DDD.equals(other.rEVISED_DDD))
				return false;
			if (sCS_IND == null) {
				if (other.sCS_IND != null)
					return false;
			} else if (!sCS_IND.equals(other.sCS_IND))
				return false;
			if (sUPPLR_CD == null) {
				if (other.sUPPLR_CD != null)
					return false;
			} else if (!sUPPLR_CD.equals(other.sUPPLR_CD))
				return false;
			if (tIME_STAMP == null) {
				if (other.tIME_STAMP != null)
					return false;
			} else if (!tIME_STAMP.equals(other.tIME_STAMP))
				return false;
			if (tOYOTA_PART_NUM == null) {
				if (other.tOYOTA_PART_NUM != null)
					return false;
			} else if (!tOYOTA_PART_NUM.equals(other.tOYOTA_PART_NUM))
				return false;
			if (tRACKING_FLG == null) {
				if (other.tRACKING_FLG != null)
					return false;
			} else if (!tRACKING_FLG.equals(other.tRACKING_FLG))
				return false;
			if (tRANSP_CD == null) {
				if (other.tRANSP_CD != null)
					return false;
			} else if (!tRANSP_CD.equals(other.tRANSP_CD))
				return false;
			if (vDR_CD == null) {
				if (other.vDR_CD != null)
					return false;
			} else if (!vDR_CD.equals(other.vDR_CD))
				return false;
			if (vDR_FILL_LT == null) {
				if (other.vDR_FILL_LT != null)
					return false;
			} else if (!vDR_FILL_LT.equals(other.vDR_FILL_LT))
				return false;
			if (vDR_XSIT_LT == null) {
				if (other.vDR_XSIT_LT != null)
					return false;
			} else if (!vDR_XSIT_LT.equals(other.vDR_XSIT_LT))
				return false;
			return true;
		}
	
	    


}