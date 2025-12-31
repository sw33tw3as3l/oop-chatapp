package com.ap.chat.client.cli;

import com.ap.chat.common.exception.InvalidCommandException;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static class ParsedCommand {
        public final String name;
        public final String[] args;

        public ParsedCommand(String name, String[] args) {
            this.name = name;
            this.args = args;
        }
    }

    public static ParsedCommand parse(String line) {
        if (line == null) throw new InvalidCommandException("Empty input");

        String s = line.trim();
        
        if (!s.startsWith("/")) throw new InvalidCommandException("Not a command");

        s = s.substring(1).trim();
        
        if (s.isEmpty()) throw new InvalidCommandException("Empty command");

        List<String> tokens = tokenize(s);
        
        String name = tokens.get(0).toLowerCase();
        String[] args = tokens.subList(1, tokens.size()).toArray(new String[0]);

        return new ParsedCommand(name, args);
    }

    private static List<String> tokenize(String s) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuote = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '"') {
                inQuote = !inQuote;
                continue;
            }

            if (!inQuote && Character.isWhitespace(c)) {
                if (cur.length() > 0) {
                    out.add(cur.toString());
                    cur.setLength(0);
                }
            } else {
                cur.append(c);
            }
        }
        
        if (cur.length() > 0) out.add(cur.toString());

        return out;
    }
}