package co.edu.poligran.linuxapi.controller;

import co.edu.poligran.linuxapi.dto.ResponseDTO;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final ChatgptService chatgptService;

    private static final String PROMPT = """
            
            Instrucciones:
            
           - Escribe una pequeña explicación detallada y clara del comando de Linux que se te pide.
           - Asegúrate de ser claro y conciso.
           - Explica el uso del comando y su aplicación.
           - Incluye ejemplo de uso.
           - Usa un lenguaje técnico adecuado pero fácil de entender.
           - La salida debe ser un texto corto y contundente de no mas de dos renglones.
           - Si no sabes el comando, debes decir exactamente: "No tengo información sobre el comando (comando)".
           - Asegurate que el comando si exista. Valida el comando antes de dar la información final.
           - No incluyas introducciones generales ni explicaciones innecesarias. 
           - Ve directamente a la explicación.
           - Inicia con la frase "Se utiliza ...".
           - No incluyas información de comandos incorrectos.
           - No incluyas información de comandos que no existen.
           - No incluyas información de comandos que no se te pidieron.
            El comando es: (comando).
            """;

    @PostMapping("/send")
    @CrossOrigin(origins = "*")
    public ResponseDTO<String> send(@RequestBody String message) {
        log.info("message is: {}", message);
        String command = message.trim();
        String promptMessage = PROMPT.replace("(comando)", command);
        log.info("completed prompt is: {}", promptMessage);
        String responseMessage = chatgptService.sendMessage(promptMessage);
        //responseMessage = responseMessage.replace("\\n", " ").replace("\\r", " ");
        log.info("response is: {}", responseMessage);

        return ResponseDTO.success(responseMessage);
    }
}
