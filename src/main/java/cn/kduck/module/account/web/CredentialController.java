package cn.kduck.module.account.web;

import cn.kduck.module.account.credential.CredentialGenerator;
import cn.kduck.module.account.service.CredentialService;
import cn.kduck.module.account.AccountConfig;
import cn.kduck.module.account.AccountConfig.CredentialConfig;
import cn.kduck.core.web.json.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/credential")
@Api(tags = "帐号凭证管理")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private AccountConfig accountConfig;


    @GetMapping("/valid")
    @ApiOperation("帐号凭证强度检测")
    public JsonObject isValid(@RequestParam("credential") String credential){

        JsonObject jsonObject = new JsonObject();

        checkLength(jsonObject,credential);

        if(!Boolean.parseBoolean(""+jsonObject.getData())){
            return jsonObject;
        }

        boolean pass = credentialService.checkCredential(credential);
        if(!pass){
            checkCredential(jsonObject, pass);

        }
        return jsonObject;
    }

    private void checkCredential(JsonObject jsonObject, boolean pass) {
        CredentialConfig credentialConfig = accountConfig.getCredentialConfig();
        Integer[] strength = credentialConfig.getStrength();
        StringBuilder messageBuilder = new StringBuilder("密码强度不符合要求，必须包含：");

        if(strength.length > 0){
            int strengthValue = 0;
            for (Integer s : strength) {
                strengthValue += s;
            }

            if((strengthValue & CredentialGenerator.NUMBER) >0){
                messageBuilder.append("数字、");
            }
            if((strengthValue & CredentialGenerator.UPPER_LETTER) >0){
                messageBuilder.append("大写字母、");
            }
            if((strengthValue & CredentialGenerator.LOWER_LETTER) >0){
                messageBuilder.append("小写字母、");
            }
            if((strengthValue & CredentialGenerator.SPECIAL) >0){
                messageBuilder.append("特殊字符、");
            }
            String errorMessage = messageBuilder.toString();
            jsonObject.setMessage(errorMessage.substring(0,errorMessage.length()-1));
        }
        jsonObject.setData(pass);
    }

    private void checkLength(JsonObject jsonObject,String credential){

        CredentialConfig credentialConfig = accountConfig.getCredentialConfig();
        int length = credential.trim().length();

        StringBuilder messageBuilder = new StringBuilder("密码强度不符合要求，必须包含");
        boolean pass = true;
        if(credentialConfig.getMinLength() > 0 && length < credentialConfig.getMinLength()){
            messageBuilder.append("长度最少" + credentialConfig.getMinLength() + "个字符");
            pass = false;
        }
        if(credentialConfig.getMaxLength() > 0 && length > credentialConfig.getMaxLength()){
            messageBuilder.append("长度最多" + credentialConfig.getMaxLength() + "个字符");
            pass = false;
        }

        jsonObject.setData(pass);
        if(!pass){
            jsonObject.setMessage(messageBuilder.toString());
        }
    }


}
