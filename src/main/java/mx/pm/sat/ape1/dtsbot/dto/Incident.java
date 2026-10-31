package mx.pm.sat.ape1.dtsbot.dto;

import java.math.BigDecimal;

public record Incident(String reqs, String originalText, String textAnalysis, BigDecimal likely) {

}
