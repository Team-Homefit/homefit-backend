package com.homefit.homefit.region.persistence.po;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SidoPo {
    private String code;
    private String name;
    private List<Sgg> sggs;

    @Getter
    @NoArgsConstructor
    public static class Sgg {
        private String code;
        private String name;
        private List<Umd> umds;

        @Override
        public String toString() {
            return "Sgg{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", umds=" + umds +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Umd {
        private String code;
        private String name;

        @Override
        public String toString() {
            return "Umd{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SidoPo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sggs=" + sggs +
                '}';
    }
}
