package cn.kduck.module.account.service.impl;

import cn.kduck.module.account.credential.CredentialGenerator;
import cn.kduck.module.account.credential.impl.CredentialGeneratorImpl;
import cn.kduck.module.account.AccountConfig;
import cn.kduck.module.account.AccountConfig.CredentialConfig;
import cn.kduck.module.account.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired(required = false)
    private CredentialGenerator credentialGenerator;

    @Autowired
    private AccountConfig accountConfig;

    @Override
    public String generateCredential() {
        CredentialConfig credentialConfig = accountConfig.getCredentialConfig();
        int strength = getStrength(credentialConfig);

        return getCredentialGenerator().generate(credentialConfig.getLength(),strength);
    }

    @Override
    public boolean checkCredential(String str) {
        CredentialConfig credentialConfig = accountConfig.getCredentialConfig();
        str = str.trim();

        if(str.indexOf(' ') >= 0){
            return false;
        }

        return str.length() >= credentialConfig.getLength() && getCredentialGenerator().isValid(str,getStrength(credentialConfig));
    }

    public CredentialGenerator getCredentialGenerator(){
        if(credentialGenerator == null){
            credentialGenerator = new CredentialGeneratorImpl();
        }
        return credentialGenerator;
    }

    private int getStrength(CredentialConfig credentialConfig) {
        int strength = 0;
        for (Integer s : credentialConfig.getStrength()) {
            strength |= s.intValue();
        }
        return strength;
    }
}
