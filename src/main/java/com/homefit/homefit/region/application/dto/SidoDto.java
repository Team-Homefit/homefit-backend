package com.homefit.homefit.region.application.dto;

import com.homefit.homefit.region.persistence.po.SidoPo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SidoDto {
    private String code;
    private String name;
    private List<Sgg> sggs;

    public static SidoDto from(SidoPo po) {
        return new SidoDto(
                po.getCode(),
                po.getName(),
                po.getSggs().stream().map(Sgg::from).toList()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Sgg {
        private String code;
        private String name;
        private List<Umd> umds;

        public static Sgg from(SidoPo.Sgg po) {
            return new Sgg(
                    po.getCode(),
                    po.getName(),
                    po.getUmds().stream().map(Umd::from).toList()
            );
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Umd {
        private String code;
        private String name;

        public static Umd from(SidoPo.Umd po) {
            return new Umd(po.getCode(), po.getName());
        }
    }
}
