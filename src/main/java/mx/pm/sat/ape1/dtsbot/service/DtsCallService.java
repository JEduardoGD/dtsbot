package mx.pm.sat.ape1.dtsbot.service;

import java.util.List;

import org.springframework.ai.chat.messages.Message;

import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.exception.DtsCallServiceException;

public interface DtsCallService {

    List<Message> loadContext() throws DtsCallServiceException;

    IncidentProcessResponse proceessCall(List<Message> messagesContextList, Incident incident);

}
