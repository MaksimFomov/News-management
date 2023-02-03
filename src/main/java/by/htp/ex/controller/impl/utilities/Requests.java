package by.htp.ex.controller.impl.utilities;

import java.util.Map;

public class Requests {
    public static String getRequest(String requestName, Map<String, String[]> requestParams) {
        StringBuilder lastRequest = new StringBuilder(requestName);
        
        try {
            Map<String, String[]> paramsMap = requestParams;
            lastRequest.append("?");
            for (var paramArray : paramsMap.entrySet()) {
                for(var param : paramArray.getValue()) {
                    lastRequest.append(paramArray.getKey()).append("=").append(param).append("&");
                }
            }
            lastRequest.deleteCharAt(lastRequest.length() - 1);
            
            return lastRequest.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
