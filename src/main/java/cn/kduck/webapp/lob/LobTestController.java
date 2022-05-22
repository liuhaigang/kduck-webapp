package cn.kduck.webapp.lob;

import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.web.json.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/lob")
@Api(tags = "测试lob保存读取")
public class LobTestController {

    @Autowired
    private DefaultService defaultService;

    private static final String DEMO = "DEMO";

    @PostMapping("/clob/add")
    @ApiOperation("添加为clob")
    public JsonObject saveAsClob(String text){
        Map<String, Object> valueMap = ParamMap.create("demoClob", text).toMap();
        defaultService.add(DEMO,valueMap);
        return new JsonObject(valueMap.get("demoId"));
    }

    @GetMapping("/clob/get")
    @ApiOperation("从clob中读取")
    public JsonObject getFormClob(String id){
        ValueMap valueMap = defaultService.get(DEMO, id);
        if(valueMap == null ){
            throw new RuntimeException("指定的数据不存在："+id);
        }
        return new JsonObject(valueMap.getValueAsString("demoClob"));
    }

    @PostMapping("/blob/add")
    @ApiOperation("添加为blob")
    public JsonObject saveAsBlob(MultipartFile file) throws IOException {
        InputStream fis = file.getInputStream();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        FileCopyUtils.copy(fis,fos);

        Map<String, Object> valueMap = ParamMap.create("demoBlob", fos.toByteArray()).toMap();
        defaultService.add(DEMO,valueMap);

        return new JsonObject(valueMap.get("demoId"));
    }

    @GetMapping("/blob/get")
    @ApiOperation("从blob中读取")
    public void getFormBlob(String id,HttpServletResponse response) throws IOException{
        ValueMap valueMap = defaultService.get(DEMO, id);
        if(valueMap == null ){
            throw new RuntimeException("指定的数据不存在："+id);
        }
        response.setContentType("application/png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write((byte[]) valueMap.get("demoBlob"));
    }
}
