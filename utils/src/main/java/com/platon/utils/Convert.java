package com.platon.utils;

import java.math.BigDecimal;

/**
 * Ethereum unit conversion functions.
 */
public final class Convert {
    private Convert() { }

    public static BigDecimal fromVon(String number, Unit unit) {
        return fromVon(new BigDecimal(number), unit);
    }

    public static BigDecimal fromVon(BigDecimal number, Unit unit) {
        return number.divide(unit.getVonFactor());
    }

    public static BigDecimal toVon(String number, Unit unit) {
        return toVon(new BigDecimal(number), unit);
    }

    public static BigDecimal toVon(BigDecimal number, Unit unit) {
        return number.multiply(unit.getVonFactor());
    }

    public enum Unit {
        VON("von", 0),
        KVON("kvon", 3),
        MVON("mvon", 6),
        GVON("gvon", 9),
        TVON("tvon", 12),
        PVON("pvon", 15),
        KPVON("kpvon", 18),
        MPVON("mpvon", 21),
        GPVON("gpvon", 24),
        TPVON("tpvon", 27);
        //MICROATP("microatp", 12),   //TVON  VON12
        //MILLIATP("milliatp", 15),   //PVON  VON15
        //ATP("atp", 18),             //KPVON VON18
        //KATP("katp", 21),           //MPVON  VON21
        //MATP("matp", 24),           //GPVON  VON24
        //GATP("gatp", 27);           //TPVON  VON27

        private String name;
        private BigDecimal vonFactor;

        Unit(String name, int factor) {
            this.name = name;
            this.vonFactor = BigDecimal.TEN.pow(factor);
        }

        public BigDecimal getVonFactor() {
            return vonFactor;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit fromString(String name) {
            if (name != null) {
                for (Unit unit : Unit.values()) {
                    if (name.equalsIgnoreCase(unit.name)) {
                        return unit;
                    }
                }
            }
            return Unit.valueOf(name);
        }
    }
}
