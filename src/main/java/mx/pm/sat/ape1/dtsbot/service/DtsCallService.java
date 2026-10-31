package mx.pm.sat.ape1.dtsbot.service;

import java.util.List;

import org.springframework.ai.chat.messages.Message;

import mx.pm.sat.ape1.dtsbot.dto.Incident;

public interface DtsCallService {

    List<Message> loadContext();

    Incident proceessCall(List<Message> contextList, String call);

}
