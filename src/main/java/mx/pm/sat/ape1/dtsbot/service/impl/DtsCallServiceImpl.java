package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.DtsCallService;
import mx.pm.sat.ape1.dtsbot.service.exception.DtsCallServiceException;
import mx.pm.sat.ape1.dtsbot.util.TextUtil;
import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

@Service
@RequiredArgsConstructor
public class DtsCallServiceImpl implements DtsCallService {

    private final ChatMemory chatMemory;
    private final ChatClient.Builder chatClientBuilder;

    private static final String CHAT_MEMORY_CONVERSATION_ID = "1";
    private static final String CONTEXT_FILE = "context.txt";

    @Override
    public List<Message> loadContext() throws DtsCallServiceException {
        File file = Paths.get(CONTEXT_FILE).toFile();
        List<String> contextTextList;
        try {
            contextTextList = TextUtil.loadTextFile(file);
        } catch (TextUtilException e) {
            throw new DtsCallServiceException(e);
        }
        List<Message> contextMessages = contextTextList.stream().map(l -> (Message) (new UserMessage(l))).toList();
        return contextMessages;
    }

    @Override
    public @Nullable IncidentProcessResponse proceessCall(List<Message> messagesContextList, Incident incident) {
        chatMemory.add(CHAT_MEMORY_CONVERSATION_ID, messagesContextList);

        String promptText = """
                Todos los mensajes de contexto anteriores forman parte de mensajes reales que determinan que un incidente pertenece a la categoria "lentitud en certisat";
                Basado en todos los mensajes de contexto determina si el siguiente mensaje pertenece a la categoria especificada y con que probabilidad lo es: %s.
                """;
        ChatClient chatClient = chatClientBuilder.build();

        @Nullable IncidentProcessResponse incidentProcessResponse = chatClient.prompt()
                .messages(new UserMessage(String.format(promptText, incident.originalText())))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, CHAT_MEMORY_CONVERSATION_ID))
                .call()
                .entity(IncidentProcessResponse.class);
        return incidentProcessResponse;
    }
}
