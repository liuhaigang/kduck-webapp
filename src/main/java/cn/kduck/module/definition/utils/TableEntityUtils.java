package cn.kduck.module.definition.utils;

import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.definition.BeanFieldDef;
import cn.kduck.core.dao.definition.FieldAliasGenerator;
import cn.kduck.core.dao.definition.TableAliasGenerator;
import cn.kduck.core.utils.SpringBeanUtils;
import cn.kduck.core.utils.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHG
 */
public final class TableEntityUtils {

    private TableEntityUtils(){}

    public static final Map<String,String> getTableNames(DataSource dataSource) throws SQLException {
        Map<String,String> tableNameMap = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            try(ResultSet tables = metaData.getTables(null,connection.getSchema(),null,new String[]{"TABLE","VIEW"})){
                while(tables.next()){
                    String tableName = tables.getString("TABLE_NAME");
                    String tableRemarks = tables.getString("REMARKS");
                    tableRemarks = tableRemarks == null || "".equals(tableRemarks) ? null : tableRemarks;
                    tableNameMap.put(tableName,tableRemarks);
                }
            }
        }
        return Collections.unmodifiableMap(tableNameMap);
    }

    public static final BeanEntityDef analyzeTable(String tableName, DataSource dataSource) throws SQLException {
        ResultSet tables = null;
        try(Connection connection = dataSource.getConnection()){
            return analyzeTable(tableName,connection);
        } catch (SQLException e) {
            throw new RuntimeException("根据JDBC获取Bean定义错误",e);
        }finally {
            if(tables != null){
                tables.close();
            }
        }
    }

    private static final BeanEntityDef analyzeTable(String tableName, Connection connection) throws SQLException {
        TableAliasGenerator tableAliasGenerator = SpringBeanUtils.getBean(TableAliasGenerator.class);
        FieldAliasGenerator fieldAliasGenerator = SpringBeanUtils.getBean(FieldAliasGenerator.class);

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null,connection.getSchema(),tableName,new String[]{"TABLE","VIEW"});

        BeanEntityDef entityDef = null;

        while(tables.next()){

            String tableRemarks = tables.getString("REMARKS");
            String alias = tableAliasGenerator.genAlias(tableName);

            StringBuilder pkbuilder = new StringBuilder();
            try(ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName)){
                while(primaryKeys.next()){
                    pkbuilder.append(primaryKeys.getString("COLUMN_NAME"));
                    pkbuilder.append(",");
                }
            }
            String[] pkColumns = pkbuilder.toString().split(",");

            List<BeanFieldDef> fieldList = new ArrayList<>();
            try(ResultSet columns = metaData.getColumns(null, null, tableName, null)){
                while (columns.next()){
                    String columnName = columns.getString("COLUMN_NAME");
                    Integer dataType = columns.getInt("DATA_TYPE");
                    String nullable = columns.getString("IS_NULLABLE");
                    String remarks = columns.getString("REMARKS");
                    int digits = columns.getInt("DECIMAL_DIGITS");

                    boolean isPk = StringUtils.contain(pkColumns,columnName);

                    String attrName = fieldAliasGenerator.genAlias(null,tableName,columnName);

                    //for oralce
                    if(dataType == Types.NUMERIC){
                        dataType = digits > 0 ? Types.NUMERIC :Types.INTEGER;
                    }

                    BeanFieldDef fieldDef = new BeanFieldDef(attrName,columnName,dataType,isPk);
                    fieldDef.setRemarks(remarks);
                    fieldList.add(fieldDef);
                }
            }
            entityDef = new BeanEntityDef("",alias,tableRemarks,tableName,fieldList);

            //处理表的外键对象
            List<BeanEntityDef> parentList = new ArrayList<>();
            ResultSet foreignKeyResultSet = metaData.getImportedKeys(null, null, tableName);
            while(foreignKeyResultSet.next()){
                String pkTablenName = foreignKeyResultSet.getString("PKTABLE_NAME");
                BeanEntityDef pkBeanEntityDef = analyzeTable(pkTablenName, connection);
                parentList.add(pkBeanEntityDef);
            }
            if(!parentList.isEmpty()){
                entityDef.setFkBeanEntityDef(parentList.toArray(new BeanEntityDef[0]));
            }

        }
        return entityDef;
    }
}
