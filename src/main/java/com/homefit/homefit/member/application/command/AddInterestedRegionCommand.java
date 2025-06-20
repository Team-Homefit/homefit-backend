package com.homefit.homefit.member.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddInterestedRegionCommand {
    private final List<String> sggCodes;

    public static AddInterestedRegionCommand of(List<String> sggCodes) {
        return new AddInterestedRegionCommand(sggCodes);
    }
}
