package com.homefit.homefit.house.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchHouseDealCommand {
    private final String sequence;
    private final Double area;
    private final LocalDate since;
    private final LocalDate until;

    public static SearchHouseDealCommand of(String sequence, Double area, LocalDate since, LocalDate until) {
        return new SearchHouseDealCommand(sequence, area, since, until);
    }
}
