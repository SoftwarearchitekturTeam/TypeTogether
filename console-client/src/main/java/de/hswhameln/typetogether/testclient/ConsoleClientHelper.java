package de.hswhameln.typetogether.testclient;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClientHelper {
    private final Scanner sc;

    public ConsoleClientHelper(Scanner sc) {
        this.sc = sc;
    }

    public Socket createSocket() throws IOException {
        System.out.print("Enter network address (localhost):");
        String target = sc.nextLine();
        if (target == null || target.isBlank()) {
            target = "localhost";
        }
        System.out.print("Enter port (12557):");
        String port = sc.nextLine();
        if (port == null || port.isBlank()) {
            port = "12557";
        }
        return new Socket(target, Integer.parseInt(port));
    }

    public User createUser() {
        return new User() {
            private final Document document = createDocument();

            @Override
            public int getId() {
                return (int)(Math.random() * 1000);
            }

            @Override
            public String getName() {
                return "my-name";
            }

            @Override
            public Document getDocument() {
                return this.document;
            }
        };
    }

    public Document createDocument() {
        return new Document() {
            @Override
            public void addChar(User author, DocumentCharacter character) {
                System.out.println("addChar: " + author.getName() + "/" + character.getStringRepresentation());
            }

            @Override
            public void removeChar(User author, DocumentCharacter character) {
                System.out.println("removeChar: " + author.getName() + "/" + character.getStringRepresentation());
            }

            @Override
            public String getFuncId() {
                return "my-func-id";
            }
        };
    }
}
