package com.coderman.monitor.model;

public class OperateWithBLOBs extends Operate {

    private String operation;

    private String controller;

    private String method;

    private String returnValue;

    private String params;

    private String errorMsg;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller == null ? null : controller.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue == null ? null : returnValue.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    @Override
    public String toString() {
        return "OperateWithBLOBs{" +
                "operation='" + operation + '\'' +
                ", controller='" + controller + '\'' +
                ", method='" + method + '\'' +
                ", returnValue='" + returnValue + '\'' +
                ", params='" + params + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }


}
