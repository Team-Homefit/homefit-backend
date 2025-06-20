package com.homefit.homefit.member.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteInterestedRegionCommand {
    private final List<String> sggCodes;

    public static DeleteInterestedRegionCommand of(List<String> sggCodes) {
        return new DeleteInterestedRegionCommand(sggCodes);
    }
}
