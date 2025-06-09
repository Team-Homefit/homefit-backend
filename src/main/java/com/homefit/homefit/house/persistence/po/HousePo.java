package com.homefit.homefit.house.persistence.po;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HousePo {
    private final String aptSeq;
    private final String sggCd;
    private final String umdCd;
    private final String umdNm;
    private final String jibun;
    private final String roadNmSggCd;
    private final String roadNm;
    private final String roadNmBonbun;
    private final String roadNmBubun;
    private final String aptNm;
    private final Integer buildYear;
    private final Double latitude;
    private final Double longitude;
    private final String houseImage;
    
    
	@Override
	public String toString() {
		return "HousePo [aptSeq=" + aptSeq + ", sggCd=" + sggCd + ", umdCd=" + umdCd + ", umdNm=" + umdNm + ", jibun="
				+ jibun + ", roadNmSggCd=" + roadNmSggCd + ", roadNm=" + roadNm + ", roadNmBonbun=" + roadNmBonbun
				+ ", roadNmBubun=" + roadNmBubun + ", aptNm=" + aptNm + ", buildYear=" + buildYear + ", latitude="
				+ latitude + ", longitude=" + longitude + ", houseImage=" + houseImage + "]";
	}
   
}
