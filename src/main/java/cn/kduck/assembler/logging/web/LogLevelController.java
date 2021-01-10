package cn.kduck.assembler.logging.web;

import cn.kduck.core.web.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logLevel")
public class LogLevelController {

    @Autowired
    private LoggingSystem loggingSystem;

    @GetMapping
    @Secured("ADMIN")
    public JsonObject getLogLevel(String loggerName){
        LogLevel level = loggingSystem.getLoggerConfiguration(loggerName).getConfiguredLevel();
        return new JsonObject(level);
    }

    @PostMapping
    public JsonObject setLogLevel(@RequestParam(name="loggerName") String loggerName,@RequestParam(name="level") String level){
        LogLevel logLevel = LogLevel.valueOf(level);
        loggingSystem.setLogLevel(loggerName,logLevel);
        return JsonObject.SUCCESS;
    }

}
