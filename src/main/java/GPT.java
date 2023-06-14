import java.util.Scanner;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class GPT {
    public static void main(String... args) {
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token);
		Scanner myObj = new Scanner(System.in);
		String message = "test"; 
		String prompt;
		while (!message.equals("quit")) {
			System.out.println("Enter message: ");
			message = myObj.nextLine();
			prompt = "Player: " + message + "\nDavid: ";
			CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt(prompt)
                .echo(true)
                .user("testing")
                .n(1)
                .build();
       		String text = service.createCompletion(completionRequest).getChoices().get(0).getText();
			System.out.println(text);
		}
        System.out.println("\nCreating completion...");
        

        service.shutdownExecutor();
    }
}