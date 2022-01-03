package cn.kduck.assembler.security;

import cn.kduck.core.web.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.kduck.core.web.GlobalErrorController.GLOBAL_ERROR_CODE;
import static cn.kduck.core.web.GlobalErrorController.GLOBAL_ERROR_MESSAGE;

public class AccessDeniedMessageHandler extends AccessDeniedHandlerImpl {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Object message = request.getAttribute(GLOBAL_ERROR_MESSAGE);
        if(message != null){
            JsonObject errorObject = new JsonObject();
            Integer errorCode = (Integer)request.getAttribute(GLOBAL_ERROR_CODE);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());

            PrintWriter writer = response.getWriter();

            errorObject.setMessage(message.toString());
            if(errorCode != null){
                errorObject.setCode(errorCode);
            }else{
                errorObject.setCode(-1);
            }


            objectMapper.writeValue(writer,errorObject);


        } else {
            super.handle(request,response,accessDeniedException);
        }
    }
}
