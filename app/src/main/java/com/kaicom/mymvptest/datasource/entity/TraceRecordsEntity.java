package com.kaicom.mymvptest.datasource.entity;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table TRACE_RECORDS_TABLE.
 */
public class TraceRecordsEntity extends com.kaicom.mymvptest.datasource.entity.AbstractBizEntity  implements Cloneable {

    private Long id ; 
    private String startTime = ""; // 开始时间
    private String endTime = ""; // 结束时间
    private String filePath = ""; // 文件路径

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	/** 设置开始时间 */
    public String getStartTime() {
        return startTime;
    }

	/** 获得开始时间 */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

	/** 设置结束时间 */
    public String getEndTime() {
        return endTime;
    }

	/** 获得结束时间 */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

	/** 设置文件路径 */
    public String getFilePath() {
        return filePath;
    }

	/** 获得文件路径 */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
