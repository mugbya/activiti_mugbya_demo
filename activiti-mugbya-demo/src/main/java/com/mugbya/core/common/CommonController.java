package com.mugbya.core.common;



import com.mugbya.core.collection.BaseDto;
import com.mugbya.core.collection.Dto;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @author mugbya
 * @version 2014-08-20.
 */
public class CommonController {
    protected Dto success(Object obj) {
        Dto result = new BaseDto();
        result.put("result", obj);
        result.put("success", true);
        return result;
    }

    protected Dto failure(String msg) {
        Dto result = new BaseDto();
        result.put("errorMessage", msg);
        result.put("success", false);
        return result;
    }

    protected Dto getParams(HttpServletRequest request) {
        Dto dto = new BaseDto();
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            dto.put(name, value);
        }
        return dto;
    }
}
