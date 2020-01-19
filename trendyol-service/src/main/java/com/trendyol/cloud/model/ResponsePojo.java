package com.trendyol.cloud.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Data
public class ResponsePojo implements Serializable {


    private int responseCode;
    private String responseMessage;
    private Object responseBody;

    public ResponsePojo(Object responseBody) {
        this.responseCode = 200;
        this.responseMessage = "Success";
        this.responseBody = responseBody;
    }

    public ResponsePojo(int responseCode, String responseMessage,HttpServletResponse response) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        response.setStatus(responseCode);
    }

    public ResponsePojo(int responseCode, String responseMessage, Object responseBody,HttpServletResponse response) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseBody = responseBody;
        response.setStatus(responseCode);
    }
}
