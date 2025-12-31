package com.ap.chat.common.protocol.payload.export;

import java.io.Serializable;

public class ExportLastReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private int n;

    public ExportLastReq() {}
    public ExportLastReq(int n) { this.n = n; }

    public int getN() { return n; }
    public void setN(int n) { this.n = n; }

    @Override
    public String toString() {
        return "ExportLastReq{n=" + n + "}";
    }
}
