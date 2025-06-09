package com.homefit.homefit.region.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import com.homefit.homefit.region.application.dto.SidoDto;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionResponse {
    private final List<Sido> sidos;

    public static RegionResponse of(List<SidoDto> dtos) {
        return new RegionResponse(dtos.stream().map(Sido::from).toList());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Sido {
        private final String code;
        private final String name;
        private final List<Sgg> sggs;

        public static Sido from(SidoDto dto) {
            return new Sido(dto.getCode(), dto.getName(), dto.getSggs().stream().map(Sgg::from).toList());
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Sgg {
        private final String code;
        private final String name;
        private final List<Umd> umds;

        public static Sgg from(SidoDto.Sgg dto) {
            return new Sgg(dto.getCode(), dto.getName(), dto.getUmds().stream().map(Umd::from).toList());
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Umd {
        private final String code;
        private final String name;

        public static Umd from(SidoDto.Umd dto) {
            return new Umd(dto.getCode(), dto.getName());
        }
    }
}
