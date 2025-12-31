package com.ap.chat.client.cli;

public class HelpPrinter {
    public static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  /help");
        System.out.println("  /exit");
        System.out.println("  /register <username>");
        System.out.println("  /login <username>");
        System.out.println("  /create <roomName>");
        System.out.println("  /join <roomName>");
        System.out.println("  /leave");
        System.out.println("  /rooms");
        System.out.println("  /users");
        System.out.println("  /upload <localPath>");
        System.out.println("  /download <fileId> <savePath>");
        System.out.println("  /export last <N> <savePath>");
        System.out.println();
        System.out.println("Any input not starting with '/' is treated as a chat message.");
    }
}