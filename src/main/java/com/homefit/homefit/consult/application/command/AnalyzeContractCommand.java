package com.homefit.homefit.consult.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyzeContractCommand {
    private final MultipartFile contractFile;
    private final Long consultRoomId;
    private final String message;
    private final Boolean isFirstChat;

    public static AnalyzeContractCommand of(MultipartFile contractFile, Long consultRoomId, String message, Boolean isFirstChat) {
        return new AnalyzeContractCommand(contractFile, consultRoomId, message, isFirstChat);
    }
}
