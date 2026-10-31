package mx.pm.sat.ape1.dtsbot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.service.DtsCallService;

@Service
@RequiredArgsConstructor
public class DtsCallServiceImpl implements DtsCallService {

    private final OpenAiChatModel openAiChatModel;

    @Override
    public List<Message> loadContext() {

        List<Message> contextList = new ArrayList<>();
        contextList.add(new UserMessage(
                "mensaje de contexto: Se reporta que el aplicativo certisat crm está tardando más de 8 minutos en generar certificados de firma electrónica."));
        contextList.add(new UserMessage(
                "mensaje de contexto: SE INFORMA LENTITUD EN EL APLICATIVO CERTISAT_CRM AL TRATAR DE GENERAR Y/O REVOCAR CERTIFICADOS, DE HASTA 10 MNUTOS"));
        contextList.add(new UserMessage(
                "mensaje de contexto: SE COMUNICA USUARIO VÍA TELEFÓNICA JOSE FLORES ORTIZ DE GRUPO ALTI REPORTANDO EXTREMA LENTITUD EN CERTISAT CRM."));
        contextList.add(new UserMessage(
                "mensaje de contexto: Lentitud en la aplicación CERTISAT CRM para la generación de certificados de primera vez y de renovación, tardando más de 6 minutos, se aplica contingencia. MS Y MST"));
        contextList.add(new UserMessage("mensaje de contexto: LENTITUD EN CERTISAT"));
        contextList.add(new UserMessage(
                "mensaje de contexto: Buen día, reportando lentitud en guardar certificado en CertiSAT (CRM), de 4 minutos, se adjunta evidencia"));
        return contextList;
    }

    @Override
    public Incident proceessCall(List<Message> contextList, String call) {
        BeanOutputConverter<Incident> outputConverter = new BeanOutputConverter<>(Incident.class);

        String promptTemplateText = """
                Todos los mensajes de contexto anteriores forman parte de mensajes reales que determinan que un incidente pertenece a la categoria "lentitud en certisat";
                Basado en todos los mensajes de contexto determina si el siguiente mensaje pertenece a la categoria especificada y con que probabilidad lo es: {call}. {format}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(promptTemplateText);
        
        Prompt prompt = promptTemplate.create(Map.of("call", call, "format", outputConverter.getFormat()));
        
        contextList.add(new UserMessage(prompt + call));

        ChatResponse chatResponseResult = openAiChatModel.call(new Prompt(contextList));

        String result = chatResponseResult.getResult().getOutput().getText();
        
        return outputConverter.convert(result);

    }
}
