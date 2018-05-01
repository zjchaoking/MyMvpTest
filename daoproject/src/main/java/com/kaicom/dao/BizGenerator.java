package com.kaicom.dao;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by jc
 */
public class BizGenerator {

    private static String ABSTRACT_BIZ_PATH = "com.kaicom.mymvptest.datasource.entity.AbstractBizEntity";
    public static void main(Schema schema) {

        //地图轨迹跟踪记录表
        createTraceRecords(schema, "TraceRecordsEntity",
                "TRACE_RECORDS_TABLE");
    }






    /**
     *
     * @param schema
     * @param entityName
     * @param tableName
     */
    private static void createTraceRecords(Schema schema, String entityName, String tableName) {
        Entity piece = schema.addEntity(entityName);
        piece.setTableName(tableName);
        // 是否添加有参构造器
        piece.setConstructors(false);
        piece.setSuperclass(ABSTRACT_BIZ_PATH);
        piece.implementsInterface("Cloneable");
        // 推荐主键
        piece.addIdProperty().autoincrement();
        // 创建单一索引，只要调用index方法即可
        piece.addStringProperty("startTime")
                .comments("开始时间");
        piece.addStringProperty("endTime")
                .comments("结束时间");
        piece.addStringProperty("filePath")
                .comments("文件路径");
    }


}
