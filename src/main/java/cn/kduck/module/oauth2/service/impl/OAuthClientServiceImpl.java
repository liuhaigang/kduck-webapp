package cn.kduck.module.oauth2.service.impl;

import cn.kduck.module.oauth2.query.ClientInfoQuery;
import cn.kduck.module.oauth2.service.ClientInfo;
import cn.kduck.module.oauth2.service.OAuthClientService;
import cn.kduck.core.dao.definition.BeanFieldDef;
import cn.kduck.core.dao.field.BeanFieldListFilter;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAuthClientServiceImpl extends DefaultService implements OAuthClientService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public void addClientInfo(ClientInfo clientInfo) {
        //TODO 密码编码
//        clientInfo.setClientSecret();
        super.add(CODE_OAUTH_CLIENT_DETAILS,clientInfo);
    }

    @Override
    public void deleteClientInfo(String[] ids) {
        super.delete(CODE_OAUTH_CLIENT_DETAILS,ids);
    }

    @Override
    public void updateClientInfo(ClientInfo clientInfo) {
        super.update(CODE_OAUTH_CLIENT_DETAILS,clientInfo);
    }

    @Override
    public ClientInfo getClientInfo(String clientId) {
        List<BeanFieldDef> fields = BeanDefUtils.excludeField(super.getFieldDefList(CODE_OAUTH_CLIENT_DETAILS), "clientSecret");
        return super.getForBean(CODE_OAUTH_CLIENT_DETAILS,clientId,new BeanFieldListFilter(fields),ClientInfo::new);
    }

    @Override
    public List<ClientInfo> listClientInfo(Map<String, Object> paramMap, Page page) {
        QuerySupport query = super.getQuery(ClientInfoQuery.class, paramMap);
        return super.listForBean(query,page,ClientInfo::new);
    }
}
