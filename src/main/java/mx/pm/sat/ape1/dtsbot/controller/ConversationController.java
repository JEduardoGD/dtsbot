package mx.pm.sat.ape1.dtsbot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mx.pm.sat.ape1.dtsbot.dto.ResponseDTO;

@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {
    
    @Value("${spring.ai.openai.api-key}")
    private String key;

    private final OpenAiChatModel openAiChatModel;

    @GetMapping("/generateConversation")
    public ResponseEntity<ResponseDTO<String>> generate(@RequestParam String message) {
        
        List<Message> messageList = new ArrayList<>();
        messageList.add(new UserMessage("mensaje de contexto: Se reporta que el aplicativo certisat crm está tardando más de 8 minutos en generar certificados de firma electrónica."));
        messageList.add(new UserMessage("mensaje de contexto: SE INFORMA LENTITUD EN EL APLICATIVO CERTISAT_CRM AL TRATAR DE GENERAR Y/O REVOCAR CERTIFICADOS, DE HASTA 10 MNUTOS"));
        messageList.add(new UserMessage("mensaje de contexto: SE COMUNICA USUARIO VÍA TELEFÓNICA JOSE FLORES ORTIZ DE GRUPO ALTI REPORTANDO EXTREMA LENTITUD EN CERTISAT CRM."));
        messageList.add(new UserMessage("mensaje de contexto: Lentitud en la aplicación CERTISAT CRM para la generación de certificados de primera vez y de renovación, tardando más de 6 minutos, se aplica contingencia. MS Y MST"));
        messageList.add(new UserMessage("mensaje de contexto: LENTITUD EN CERTISAT"));
        messageList.add(new UserMessage("mensaje de contexto: Buen día, reportando lentitud en guardar certificado en CertiSAT (CRM), de 4 minutos, se adjunta evidencia"));


        String prompt = """
                Todos los mensajes de contexto anteriores forman parte de mensajes reales que determinan que un incidente pertenece a la categoria "lentitud en certisat";
                Basado en todos los mensajes de contexto determina si el siguiente mensaje pertenece a la categoria especificada y con que probabilidad lo es: 
                """;
        messageList.add(new UserMessage(prompt + message));
        
        ChatResponse chatResponseResult = openAiChatModel.call(new Prompt(messageList));
        
        String textResponse = chatResponseResult.getResult().getOutput().getText();
        return ResponseEntity.ok(new ResponseDTO<>(200, "SUCCESS", textResponse));
    }

}
