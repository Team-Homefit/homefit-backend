package com.homefit.homefit.house.application.dto;

import com.homefit.homefit.house.persistence.po.HousePo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseDto {
    private final String aptSequence;
    private final String sggCode;
    private final String umdCode;
    private final String umdName;
    private final String jibun;
    private final String roadNameSggCode;
    private final String roadName;
    private final String roadNameBonbun;
    private final String roadNameBubub;
    private final String aptName;
    private final Integer buildYear;
    private final Double latitude;
    private final Double longitude;
    private final String image;

    public static HouseDto from(HousePo po) {
        return new HouseDto(
                po.getAptSeq(),
                po.getSggCd(),
                po.getUmdCd(),
                po.getUmdNm(),
                po.getJibun(),
                po.getRoadNmSggCd(),
                po.getRoadNm(),
                po.getRoadNmBonbun(),
                po.getRoadNmBubun(),
                po.getAptNm(),
                po.getBuildYear(),
                po.getLatitude(),
                po.getLongitude(),
                po.getHouseImage()
            );
    }

	@Override
	public String toString() {
		return "HouseDto [aptSequence=" + aptSequence + ", sggCode=" + sggCode + ", umdCode=" + umdCode + ", umdName="
				+ umdName + ", jibun=" + jibun + ", roadNameSggCode=" + roadNameSggCode + ", roadName=" + roadName
				+ ", roadNameBonbun=" + roadNameBonbun + ", roadNameBubub=" + roadNameBubub + ", aptName=" + aptName
				+ ", buildYear=" + buildYear + ", latitude=" + latitude + ", longitude=" + longitude + ", image="
				+ image + "]";
	}
}
