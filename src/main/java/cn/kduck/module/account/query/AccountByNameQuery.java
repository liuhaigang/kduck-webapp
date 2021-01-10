package cn.kduck.module.account.query;

import cn.kduck.module.account.service.AccountService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LiuHG
 */
@Component
public class AccountByNameQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(AccountService.CODE_ACCOUNT);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.where().and("account_name", ConditionType.EQUALS,"accountName",true);
        return sqlBuilder.build();
    }
}
