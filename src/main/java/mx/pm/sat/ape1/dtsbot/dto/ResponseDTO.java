package mx.pm.sat.ape1.dtsbot.dto;

public record ResponseDTO<T>(int status, String message, T data) {
}