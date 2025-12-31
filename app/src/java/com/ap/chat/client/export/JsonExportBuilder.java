package com.ap.chat.client.export;

import com.ap.chat.common.model.Message;
import com.ap.chat.common.util.TimeUtil;

import java.util.List;

public class JsonExportBuilder {

    public static String build(String room, long exportedAtMillis, List<Message> messages) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"room\": \"").append(escape(room)).append("\",\n");
        sb.append("  \"exportedAt\": \"").append(TimeUtil.isoFromMillis(exportedAtMillis)).append("\",\n");
        sb.append("  \"messages\": [\n");

        if (messages != null) {
            for (int i = 0; i < messages.size(); i++) {
                Message m = messages.get(i);
                sb.append("    {\n");
                sb.append("      \"id\": \"").append(escape(m.getId())).append("\",\n");
                sb.append("      \"sender\": \"").append(escape(m.getSender())).append("\",\n");
                sb.append("      \"timestamp\": ").append(m.getTimestamp()).append(",\n");
                sb.append("      \"type\": \"").append(m.getType()).append("\",\n");
                sb.append("      \"content\": \"").append(escape(m.getContent())).append("\"");

                if (m.getFileId() != null) {
                    sb.append(",\n      \"fileId\": \"").append(escape(m.getFileId())).append("\",\n");
                    sb.append("      \"fileName\": \"").append(escape(m.getFileName())).append("\"\n");
                } else {
                    sb.append("\n");
                }

                sb.append("    }");
                if (i < messages.size() - 1) sb.append(",");
                sb.append("\n");
            }
        }

        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\': out.append("\\\\"); break;
                case '"': out.append("\\\""); break;
                case '\n': out.append("\\n"); break;
                case '\r': out.append("\\r"); break;
                case '\t': out.append("\\t"); break;
                default:
                    if (c < 32) out.append(String.format("\\u%04x", (int)c));
                    else out.append(c);
            }
        }
        return out.toString();
    }
}