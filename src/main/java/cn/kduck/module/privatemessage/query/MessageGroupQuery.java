package cn.kduck.module.privatemessage.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.privatemessage.service.PrivateMessageService.CODE_MESSAGE_GROUP;

@Component
public class MessageGroupQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(CODE_MESSAGE_GROUP);

        SelectBuilder selectBuilder = new SelectBuilder(entityDef,paramMap);
        selectBuilder.where().and("EXPIRED_DATE", ConditionType.LESS_OR_EQUALS,"expiredDate");
        return selectBuilder.build();
    }
}
