package com.eldoraludo.ppafadministration.stream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelStreamResponse implements StreamResponse {

    private ByteArrayOutputStream baos;
    private String filename;

    public ExcelStreamResponse(ByteArrayOutputStream baos, String filename) {
        this.baos = baos;
        this.filename = filename;
    }

    public String getContentType() {
        return "application/vnd.ms-excel";
    }

    public InputStream getStream() throws IOException {
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public void prepareResponse(Response response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".xls\"");
    }
}
