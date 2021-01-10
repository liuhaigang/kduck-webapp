package cn.kduck.module.oauth2.query;

import cn.kduck.module.oauth2.service.OAuthClientService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientInfoQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(OAuthClientService.CODE_OAUTH_CLIENT_DETAILS);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.bindFields("", BeanDefUtils.excludeField(entityDef.getFieldList(),"clientSecret"));
        return sqlBuilder.build();
    }
}
