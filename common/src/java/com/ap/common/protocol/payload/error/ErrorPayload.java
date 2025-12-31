package com.ap.chat.common.protocol.payload.error;

import java.io.Serializable;

public class ErrorPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;

    public ErrorPayload() {}
    public ErrorPayload(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "ErrorPayload{code='" + code + "', message='" + message + "'}";
    }
}
