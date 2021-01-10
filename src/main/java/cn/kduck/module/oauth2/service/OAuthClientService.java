package cn.kduck.module.oauth2.service;

import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

public interface OAuthClientService {

    String CODE_OAUTH_CLIENT_DETAILS = "OAUTH_CLIENT_DETAILS";

    void addClientInfo(ClientInfo clientInfo);

    void deleteClientInfo(String[] ids);

    void updateClientInfo(ClientInfo clientInfo);

    ClientInfo getClientInfo(String clientId);

    List<ClientInfo> listClientInfo(Map<String,Object> paramMap, Page page);
}
