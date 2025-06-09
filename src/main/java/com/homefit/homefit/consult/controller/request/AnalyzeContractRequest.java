package com.homefit.homefit.consult.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyzeContractRequest {
    @NotNull(message = "계약서 파일은 필수입니다")
    private final MultipartFile contractFile;
    private final Long consultRoomId;
    private final String message;
    @NotNull(message = "첫 메시지 플래그는 필수입니다")
    private final Boolean isFirst;
}
