package cn.kduck.module.account.service;

public interface CredentialService {

    String generateCredential();

    boolean checkCredential(String str);

}
