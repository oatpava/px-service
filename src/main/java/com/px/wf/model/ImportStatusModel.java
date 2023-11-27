package com.px.wf.model;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Oat
 */
public class ImportStatusModel {

    private boolean isSuccess;
    private int statusCode;
    private Status responseStatus;
    private String errorMessage;

    public ImportStatusModel() {
        this.isSuccess = true;
    }

    public ImportStatusModel(int statusCode, String errorMessage) {
        this.isSuccess = false;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;

        switch (statusCode) {
            case 400:
                responseStatus = Response.Status.BAD_REQUEST;
                break;
            case 401:
                responseStatus = Response.Status.UNAUTHORIZED;
                break;
            case 404:
                responseStatus = Response.Status.NOT_FOUND;
                break;
            default:
                responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
        }
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Status getResponseStatus() {
        return responseStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
