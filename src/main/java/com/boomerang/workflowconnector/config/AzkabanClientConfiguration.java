package com.boomerang.workflowconnector.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * Created by kanhaiya on 28/09/16.
 */
@Data
@Getter
@Setter
@ToString
@Slf4j
public class AzkabanClientConfiguration {

    @NotEmpty
    @URL
    private String httpUrl;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    private String sessionId;

    public void setSessionId() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse =  Unirest.post(httpUrl)
                .queryString("action", "login")
                .queryString("username", userName)
                .queryString("password", password)
                .asJson();
      sessionId = jsonResponse.getBody().getObject().get("session.id").toString();
        log.info("the json response is " +  jsonResponse.getBody().toString());

    }
}
