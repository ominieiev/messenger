package com.example.messenger.servises;

import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class MainService {
    private static final String PATH_TO_FILE = "./src/main/resources/static/holder.txt";
    private static final String MESSAGE_FOR_CHAT_CLEANING = "66f561bb666f561bb6bn837nggfddsgjkkrrtyesfdg2b68372213e00fbf4a2cd6519e0cfcb";
    private static final String MESSAGE_DELIMITER = "&&##$$";

    @GetMapping("/getChat")
    String getChat() {
        return readChatFile();
    }

    @PostMapping("/addToChat")
    String addToChat(@RequestParam("message") String newMessege) {
        if (newMessege.equals(MESSAGE_FOR_CHAT_CLEANING)) {
            cleanChatFile();
        } else {
            addToChatFile(newMessege);
        }
        return readChatFile();
    }

    private void addToChatFile(String newMessege) {
        String chatFile = readChatFile();
        chatFile = chatFile + MESSAGE_DELIMITER + newMessege;
        writeToChatFile(chatFile);
    }

    private String readChatFile() {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(PATH_TO_FILE)));
        } catch (IOException e) {
            content = "error file reading";
            e.printStackTrace();
        }
        return content;
    }

    private void writeToChatFile(String text) {
        try {
            FileWriter myWriter = new FileWriter(PATH_TO_FILE);
            myWriter.write(text);
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private void cleanChatFile() {
        writeToChatFile("");
    }
}
