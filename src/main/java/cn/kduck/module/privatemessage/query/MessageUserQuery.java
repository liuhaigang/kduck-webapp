package cn.kduck.module.privatemessage.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.privatemessage.service.PrivateMessageService.CODE_MESSAGE_USER;
import static cn.kduck.module.privatemessage.service.PrivateMessageService.CODE_PRIVATE_MESSAGE;

@Component
public class MessageUserQuery implements QueryCreator {
    
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef userEntityDef = depository.getEntityDef(CODE_MESSAGE_USER);
        BeanEntityDef messageEntityDef = depository.getEntityDef(CODE_PRIVATE_MESSAGE);

        SelectBuilder selectBuilder = new SelectBuilder(paramMap);
        selectBuilder.bindFields("m",messageEntityDef.getFieldList())
                .bindFields("u", BeanDefUtils.includeField(userEntityDef.getFieldList(),"isRead"));

        selectBuilder.from("m",messageEntityDef).innerJoinOn("u",userEntityDef,"messageId").where()
                .and("u.USER_ID", ConditionType.EQUALS,"userId").orderBy().asc("u.IS_READ");

        return selectBuilder.build();
    }
}
