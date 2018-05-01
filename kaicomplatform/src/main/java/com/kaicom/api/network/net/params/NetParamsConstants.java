/**
 * NetParamsConstants.java
 *
 * 功  能： 
 * 类名： 常量
 *
 *   ver     变更日      部门       责任人     变更内容
 * ──────────────────────────────────
 *   V1.00   '14-01-17   恒生   陈宏亮    初版
 */
package com.kaicom.api.network.net.params;


/**
 * 用于存放常量.
 * 
 * @author chenhl
 * 
 */
public class NetParamsConstants {

    /**
     * RISK.
     */
    public static final int SMALLEST_RISK = 1;

    /**
     * authIp.
     */
    public static final String AUTH_IP = "authIp";

    /**
     * regIp.
     */
    public static final String REG_IP = "regIp";
    /**
     * client id.
     */
    public static final String CLIENT_ID = "client_id";

    /**
     * client secret.
     */
    public static final String CLIENT_SECRET = "client_secret";

    /**
     * grant type.
     */
    public static final String GRANT_TYPE = "grant_type";

    /**************** 首页固定名称 *********************/
    /**
     * 七日年华收益.
     */
    public static final String WEEK_RATE = "weekRate";
    /**
     * 起购金额.
     */
    public static final String BUY_MIN_VAL = "buyMinVal";
    /**
     * 最高金额.
     */
    public static final String BUY_MAX_VAL = "buyMaxVal";
    /**
     * 购买人数.
     */
    public static final String BUY_NUM = "buyPeopleNum";

    /**
     * 风险.
     */
    public static final String RISK = "risk";

    /**
     * 费率.
     */
    public static final String SUB_FEE = "subFeeRate";
    /*************************************/

    /**************** 基金超市固定名称 *********************/
    /**
     * 升序.
     */
    public static final String SORT_UP = "a";
    /**
     * 降序.
     */
    public static final String SORT_DOWN = "d";
    /**
     * 最新净值排序字段缩写.
     */
    public static final String NAV = "n";
    /**
     * 最新净值排序字段.
     */
    public static final String NAV_VALUE = "nav";
    /**
     * 七日年化排序字段.
     */
    public static final String WEEKVALUE = "w";
    /**
     * 近一个月收益排序字段.
     */
    public static final String MONTHRATE = "m";
    /**
     * 涨跌幅排序字段.
     */
    public static final String DAY = "d";
    /**
     * 近一月收益排序.
     */
    public static final String MONTH_RATE = "monthRate";
    /**
     * 万份收益排序字段缩写.
     */
    public static final String TENTHOUSAND = "t";
    /**
     * 万份收益排序字段.
     */
    public static final String TENTHOUSAND_INCONE = "tenThousandIncome";
    /**
     * 购买人数排序字段.
     */
    public static final String BUYNUM = "b";
    /*************************************/
    /**
     * 交易确认份额.
     */
    public static final String ENSURE_SHARES = "cfmedVol";
    /**
     * 交易确认金额.
     */
    public static final String ENSURE_MONEY = "cfmedAmt";
    /**
     * 账户ID.
     */
    public static final String USER_SYS_ID = "userSysId";
    /**
     * 分组标识.
     */
    public static final String GROUP = "group";
    /**
     * 证件类型.
     */
    public static final String CERT_TYPE = "certType";
    /**
     * 证件号.
     */
    public static final String CERT_NO = "certificateNo";
    /**
     * 实名.
     */
    public static final String REAL_NAME = "realName";
    /**
     * dcType.
     */
    public static final String DC_TYPE = "dcType";
    /**
     * 用户状态.
     */
    public static final String USER_STATE = "userStat";
    /**
     * 是否已经绑卡.
     */
    public static final String IS_BIND_CARD = "isBindCard";
    /**
     * 是否已经设置交易密码.
     */
    public static final String IS_SET_TRADE_PWD = "isSetTradePWD";
    /**
     * 银行代码.
     */
    public static final String BANK_CODE = "bankCode";
    /**
     * 银行名称.
     */
    public static final String BANK_NAME = "bankName";
    /**
     * 操作内容.
     */
    public static final String OPER_COTENT = "operCotent";
    /**
     * 总分数.
     */
    public static final String TOTLE_SCORE = "totalScore";
    /**
     * 银行卡绑定ID.
     */
    public static final String ACC_BANK_ID = "acctBankId";
    /**
     * 流水号.
     */
    public static final String APPLICATION_NO = "applicationNo";
    /**
     * 机构流水号.
     */
    public static final String ORIGINAL_APPLICATION_NO = "originalApplicationNo";
    /**
     * 响应码.
     */
    public static final String RESP_CODE = "respCode";
    /**
     * 响应描述.
     */
    public static final String RESP_DESC = "respDesc";
    /**
     * 是否无密支付.
     */
    public static final String HAS_PWD = "hasPwd";
    /**
     * 用户名.
     */
    public static final String USER_NAME = "userName";
    /**
     * 开始日期.
     */
    public static final String START_DATE = "beginDate";
    /**
     * 结束日期.
     */
    public static final String END_DATE = "endDate";
    /**
     * 手机号.
     */
    public static final String MOBILE_NO = "mobile";

    /**
     * 风险类型.
     */
    public static final String RISK_TYPE = "riskType";

    /**
     * 排序类型.
     */
    public static final String ORDER_BY = "orderBy";

    /**
     * 风险类型.
     */
    public static final String ORDER_TYPE = "orderType";

    /**
     * 验证码.
     */
    public static final String VERIFY_CODE = "verifyCode";
    /**
     * 交易密码.
     */
    public static final String TRADE_PWD = "tradePassword";

    /**
     * 旧交易密码.
     */
    public static final String OLD_TRADE_PWD = "oldTradePwd";

    /**
     * 新交易密码.
     */
    public static final String NEW_TRADE_PWD = "newTradePwd";

    /**
     * 登录密码.
     */
    public static final String LOGIN_PWD = "loginPassword";

    /**
     * 旧登录密码.
     */
    public static final String OLD_LOGIN_PWD = "oldLoginPwd";

    /**
     * 新登录密码.
     */
    public static final String NEW_LOGIN_PWD = "newLoginPwd";

    /**
     * 基金类型.
     */
    public static final String FUND_TYPE = "fundType";
    /**
     * 基金代码.
     */
    public static final String FUND_CODE = "fundCode";
    /**
     * 基金名称.
     */
    public static final String FUND_NAME = "fundName";

    /**
     * 基金管理机构名称distributorName.
     */
    public static final String DISTRIBUTOR_NAME = "distributorName";
    /**
     * fundSize基金规模.
     */
    public static final String FUND_SIZE = "fundSize";

    /**
     * accumulativeNav累计收益又名累计净值.
     */
    public static final String ACCUMULATE_NAV = "accumulativeNav";
    /**
     * grade 评级.
     */
    public static final String GRADE = "grade";
    /**
     * subFeeRate 交易费率.
     */
    public static final String SUBFEE_RATE = "subFeeRate";
    /**
     * subBuyFeeRateDiscount 折扣.
     */
    public static final String SUBFEE_RATE_DISCOUNT = "subBuyFeeRateDiscount";
    /**
     * 收费方式.
     */
    public static final String BUY_FEE_TYPE = "shareClass";
    /**
     * 销售机构ID.
     */
    public static final String INSTI_ID = "instiId";
    /**
     * 销售机构code.
     */
    public static final String INSTI_CODE = "instiCode";
    /**
     * 销售机构名称instiName.
     */
    public static final String INSTI_NAME = "instiName";
    /**
     * 销售机构列表institutionsList.
     */
    public static final String INSTI_List = "institutionsList";
    /**
     * 申请金额.
     */
    public static final String AMOUNT = "amount";
    /**
     * 交易类型.
     */
    public static final String TRANS_TYPE = "transType";
    /**
     * 收费方式.
     */
    public static final String CHARGE_TYPE = "chargeType";
    /**
     * 赎回方式.
     */
    public static final String TYPE = "type";
    /**
     * 是否同意承受超出风险.
     */
    public static final String IS_AGREE_RISK = "isAgreeRisk";
    /**
     * 是否同意顺延至下一工作日.
     */
    public static final String IS_FORCE_DEAL = "isForceDeal";
    /**
     * 巨额赎回标志.
     */
    public static final String HUGE_SUM = "hugeSum";
    /**
     * 消息扩展.
     */
    public static final String EXTENSION = "extension";
    /**
     * 用户id.
     */
    public static final String USER_ID = "user_id";
    /**
     * 银行卡类别.
     */
    public static final String CARD_TYPE = "cardType";
    /**
     * 银行卡号.
     */
    public static final String CARD_NO = "cardNo";
    /**
     * 持卡人名字.
     */
    public static final String CARD_NAME = "cardName";
    /**
     * 用户类型.
     */
    public static final String USER_TYPE = "userType";
    /**
     * 用户性别.
     */
    public static final String USER_GENDER = "gender";
    /**
     * 模糊查询.
     */
    public static final String CODE_OR_NAME = "codeOrName";
    /**
     * 客户端类型.
     */
    public static final String CLIENT_TYPE = "clientType";

    /**
     * uuid.
     */
    public static final String UUID = "uuid";

    /**
     * 参数版本.
     */
    public static final String VERSION = "version";
    /**
     * 请求条数.
     */
    public static final String COUNT = "count";

    /* --- json返回结构 start --- */

    /**
     * 客户端请求ID.
     */
    public static final String EVENT_ID = "eventId";
    /**
     * 返回数据.
     */
    public static final String DATA = "data";
    /**
     * 返回提示.
     */
    public static final String MSG = "msg";
    /**
     * 返回错误号.
     */
    public static final String RETURN_CODE = "returnCode";
    /**
     * 返回是否成功.
     */
    public static final String SUCCESS = "success";

    /**
     * 业务，类型.
     */
    public static final String BUSI_CODE = "busiCode";

    /**
     * 申请份额.
     */
    public static final String QUANTITUY = "quantity";

    /**
     * 状态.
     */
    public static final String STATUS = "status";

    /**
     * 基金可以用总份额.
     */
    public static final String AVAILABLEVOL = "availableVol";

    /**
     * 是否可赎回.
     */
    public static final String ISREDEEMABLE = "isRedeemable";
    /**
     * 交易账号.
     */

    public static final String TRANSACTION_ACCOUNT_ID = "transactionAccountID";

    /**
     * 货币基金未付收益金额.
     */
    public static final String UNDISTRIBUTEMONETARYINCOME = "undistributeMonetaryIncome";

    /**
     * 货币基金未付收益金额正负.
     */
    public static final String UNDISTRIBUTEMONETARYINCOMEFLAG = "undistributeMonetaryIncomeFlag";

    /**
     * 基金总份额.
     */
    public static final String TOTALVOLOFDISTRIBUTORINTA = "totalVolOfDistributorInTa";
    /**
     * 基金公司交易账号.
     */
    public static final String TRANSACTIONACCOUNTID = "transactionAccountID";
    /**
     * 签名.
     */
    public static final String SIGN = "sign";
    /**
     * cp特征码.
     */

    public static final String ORDERKEY = "orderKey";
    /**
     * 基金公司申请单据流水号.
     */

    public static final String APPSHEETSERIALNO = "appSheetSerialNo";
    /**
     * 下单商户号.
     */
    public static final String MERCHANTID = "merchantId";
    /**
     * 交易所属日期.
     */

    public static final String TRANSACTIONDATE = "transactionDate";

    /**
     * 交易发生时间.
     */

    public static final String TRANSCATIONTIME = "transactionTime";

    /**
     * 付款时间.
     */

    public static final String PAYTIME = "payTime";
    /**
     * 基金风险评测id.
     */
    // start
    public static final String TOPICID = "topicId";
    /**
     * 基金风险评测序号.
     */
    public static final String TOPICSN = "topicSn";
    /**
     * 基金风险评测.
     */
    public static final String TOPIC = "topic";
    /**
     * 基金风险评测选项a.
     */
    public static final String AOPTION = "aOption";
    /**
     * 基金风险评测选项b.
     */
    public static final String BOPTION = "bOption";
    /**
     * 基金风险评测选项c.
     */
    public static final String COPTION = "cOption";
    /**
     * 基金风险评测选项d.
     */
    public static final String DOPTION = "dOption";
    /**
     * 基金风险评测选项e.
     */
    public static final String EOPTION = "eOption";
    /**
     * 基金风险评测选项a分数.
     */
    public static final String ASCORE = "aScore";
    /**
     * 基金风险评测选项b分数.
     */
    public static final String BSCORE = "bScore";
    /**
     * 基金风险评测选项c分数.
     */
    public static final String CSCORE = "cScore";
    /**
     * 基金风险评测选项d分数.
     */
    public static final String DSCORE = "dScore";
    /**
     * 基金风险评测选项e分数.
     */
    public static final String ESCORE = "eScore";
    // 基金风险评测 end

    /**
     * 申购金额.
     */
    public static final String PAY_MONEY = "payMoney";

    /**
     * 赎回份额.
     */
    public static final String APPVOL = "appVol";

    /**
     * 确认状态.
     */
    public static final String CONFIRMSTATUS = "confirmStatus";

    /**
     * 分组类型.
     */
    public static final String GROUPTYPE = "groupType";
    /**
     * 分组数据.
     * 
     */
    public static final String GROUPDATA = "groupData";
    /* --- json返回结构 end --- */
    /**
     * 总资 产.
     */
    public static final String TOTALAMOUNT = "totalAmount";
    /**
     * 未付收益.
     */
    public static final String TOTALUNPAYVOL = "totalUnpayVol";
    /**
     *  申购在途资产.
     */
    public static final String APPINCOME = "appIncome";
    /**
     *  是否实名认证.
     */
    public static final String IDENTITYVALIDSTAT = "identityValidStat";

    /**
     *  是否已经做风险评测.
     */
    public static final String ISRISK = "isRisk";

    /**
     *  参数更新key值.
     */
    public static final String KEY = "key";
    /**
     *  参数更新值.
     */
    public static final String VALUE = "value";
    /**
     *  参数更新版本号.
     */
    public static final String VESION = "vesion";
    /**
     *  参数更新描述.
     */
    public static final String DESCRIPTION = "description";
    /**
     *  参数更新客户端类型.
     */
    public static final String CLIENTTYPE = "clientType";
    /**
     *  单笔限额.
     */

    public static final String SINGLELIMIT = "singleLimit";
    /**
     *  最高限额.
     */

    public static final String HIGHESTLIMIT = "highestLimit";

    /**
     *  交易来源.
     */
    public static final String COMEFROM = "comeFrom";
    /**
     *  资产净值.
     */

    public static final String NETASSET = "netAsset";
    /**
     *  操作时间.
     */
    public static final String CREATETIME = "createTime";
    /**
     *  操作状态.
     */
    public static final String OPERSTATUS = "operStatus";
    /**
     *  操作码.
     */

    public static final String OPERCODE = "operCode";

    /**
     *  赎回最小份额.
     */
    public static final String REDEEMMINSHARE = "redeemMinShare";
    /**
     *  赎回最大份额.
     */
    public static final String REDEEMMAXSHARE = "redeemMaxShare";
    /**
     *  货币基金净值日期.
     */
    public static final String NAVCURPUBLISHDATE = "navCurPublishDate";
    /**
     *  非货币基金净值日期.
     */
    public static final String NAVPUBLISHDATE = "navPublishDate";
    /**
     *  购买人数.
     */
    public static final String CHARGECOUNT = "chargeCount";
    /** 
     * 日涨跌幅.
     */
    public static final String DAYRATE = "dayRate";

    /**
     *  登记份额basisforCalculatingDividend.
     */
    public static final String DIVIDENTSSHARECOUNT = "basisforCalculatingDividend";
    /**
     *  红利资金.
     */
    public static final String DIVIDENDAMOUNT = "dividendAmount";
    /**
     * 分红金额.
     */
    public static final String CONFIRMEDAMOUNT = "confirmedAmount";
    
    /**
     *  分红方式.
     */
    public static final String DIVIDENDTYPE = "dividendType";
    /**
     *  红利/红利再投资基数.
     */
    public static final String DIVIDENDFORREINVESTMENT = "volOfDividendforReinvestment";
    /**
     *  分红ID.
     */
    public static final String DIVIDENDID = "dividendId";
    /**
     *  权益登记日期.
     */
    public static final String REGISTRATIONDATE = "registrationDate";
    /**
     *  分红日.
     */
    public static final String DIVIDENTDATE = "dividentDate";
    /**
     *  单位基金分红金额（含税）.
     */
    public static final String DIVIDENDPERUNIT = "dividendPerUnit";
    /**
     *  除权日净值 nAV.
     */
    public static final String DIVIDENTNAV = "nav";
    /**
     *  是否允许修改分红方式.
     */
    public static final String CANMODIFY = "canModify";
    /**
     * 原分红方式defDividendMethod .
     */
    public static final String DEFDIVIDENDMETHOD = "defDividendMethod";
   
   /**
    * 邮箱.
    */
   public static final String  EMAIL="email";
 /**
  * 图片base64.
  */
   public static final String  IMGBASE64="imgBase64";
   /**
    * 保存全路径url.
    */
   public static final String  URL="url";
   /**
    * 图片名.
    */
   public static final String  IMGNAME="imgName";
   /**
    * 图片扩展名.
    */
   public static final String  EXTNAME="extName";
   /**
    * 图片排序.
    */
   public static final String  SEQUENCY="sequency";
  /**
   * 走势图,月.
   */
   public static final String  MONTH="month";
   /**
    * 发布日期.
    */
   public static final String PUBLISHDATE = "publishDate";
   /**
    * 输入日期.
    */
   public static final String ENTRYDATE = "entryDate";
   /**
    * 发布时间.
    */
   public static final String ENTRYTIME = "entryTime";
   /**
    * 发布日期.
    */
   public static final String CONTENT = "content";
   
   /*跨行转账 start 第三方web页面入参定义，勿改动*/
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_TRANSCODE="transCode";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_CHANNELID="channelId";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_USERNAME="userName";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_REALNAME="realName";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_IDCARDNO="idCardNo";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_MOBILENO="mobileNo";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_TRANSTYPE="transType";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_FEEFLAG="feeFlag";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_ENCODETYPE="encodeType";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_DESKEY="desKey";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_MAC="mac";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_KEY="key";
   /**
    * 跨行转账,第三方提供，参考文档.
    */
   public static final String  IBT_STRBODY="strBody";
   /*跨行转账 end*/
   /**
    * 定投相关参数.
    */
   /**
    * 定投名称.
    */
   public static final String  SCHEDULEDINVESTNAME="scheduledInvestName";
  /**
   * 定投频率.
   */
   public static final String  SCHEDULEDINVESTFREQUENCY="scheduledInvestFrequency";
   /**
    * 定投频率系数.
    */
   
   public static final String  SCHEDULEDINVESTFREQUENCYCOFFICIENT="scheduledInvestFrequencyCoefficient";
   /**
    * 定投每周期发送日.
    */
   public static final String  SCHEDULEDINVESTSENDDAY="scheduledInvestSendDay";
   /**
    * 定投ID.
    */
   public static final String  SCHEDULEDID="scheduledId";
   /**
    * 定时定额申购起始日期.
    */
   public static final String  SCHEDULEDINVESTSTARTDATE="scheduledInvestStartDate";
   /**
    * 累计确认次数.
    */
  public static final String  TOTALSUCCESSNUM="totalSuccessNum";
  /**
   * 累计确认金额.
   */
  public static final String  TOTALSUCCESSAMOUNT="totalSuccessAmount";
   
  /**
   * 开始.
   */
  public static final String  BEGIN="begin";
  
  /**
   * 图片id.
   */
  public static final String IMGID = "imgId";
  /**
   * 图片类型id.
   */
  public static final String TYPEID = "typeId";
  /**
   * 基金定投状态.
   */
  public static final String PERIODICSTATUS = "periodicStatus";
  /**
   * 最大定投量.
   */
  public static final String  PERIODICMAXVAL="periodicMaxVal";
  /**
   * 最小定投量.
   */
  public static final String  PERIODICMINVAL="periodicMinVal";
  /**
   * 定投计划状态 .
   */
  public static final String STATE="state";
  /**
   * 交易状态 .
   */
  public static final String TRADESTATUS="tradeStatus";
  /**
   * 支持银行列表 .
   */
  public static final String SUPPORTBANK="support_bank";
  /**
   * 新闻标题.
   */
  public static final String TITLE="title";
  /**
   * 新闻分类.
   */
  public static final String  NTYPECODE="nTypeCode";
  /**
   * 新闻ID.
   */
  public static final String  NEWSTEXTID = "newsTextId";
  
  /**
   * 0-未被收藏；1-已经被收藏.
   */
  public static final String  ISCOLLECTEDFLAG = "isCollectedFlag";
  /**
   * 收藏ID.
   */
  public static final String  COLLECTID="collectId";
  /**
   * 收藏数目.
   */
 public static final String COLLECTIONSCOUNT="collectionsCount";
 /**
  * 第三方类型.
  */
 public static final String TRITYPE="triType";
 /**
  * 帐号.
  */

 public static final String ACCTNO="acctNo";
 /**
  * 三方帐号id.
  */
 public static final String TRIACCOUNTID="triAccountId";
 /**
  * 基金管理人.
  */
 public static final String FUNDMANAGERCOMPANY="fundManagementCompany";
 /**
  * 基金托管人.
  */
 public static final String FUNDCUSTODIAN="fundCustodian";
 /**
  * 基金经理.
  */
 public static final String FUNDMANAGER="fundManager";
 /**
  * 占比.
  */
 public static final String PERCENTAGE="percentage";
 /**
  * 主推客户端号.
  */
 public static final String PUSHUSERID="push_user_id";
 /**
  * 主推渠道编号.
  */
 public static final String PUSHGATEID="push_gate_id";
 /**
  * 主推消息流水号.
  */ 
 public static final String PUSHMSGID="push_msg_id";
 /**
  * 内容格式.
  */
 public static final String CONTENTTYPE="content_type";
 /**
  * 终端类型.
  */
 public static final String PUSHCTYPE="push_ctype";
 /**
  * 设备号.
  */
 public static final String DEVICETOKEN="device_token";
 /**
  * 主推消息标题.
  */
 public static final String PUSHMSGTITLE="push_msg_title";
 /**
  * 主推消息摘要. 
  */
 public static final String PUSHMSGDIGEST="push_msg_digest";
 /**
  * 消息内容 .
  */
 public static final String MSGCONTENT="msg_content";
 
/**
 * 创建日期.
 */
 public static final String CREATEDATE="create_date";
 /**
  * 基金代码  .
  */
 public static final String CODE="code";
 /**
  * 基金 名称 .
  */
 public static final String NAME="name";
 /**
  * 众禄登陆接口返回的信息.
  */
 
 public static final String XMLMSG="xmlMsg";
 /**
  * 是否已经注册.
  */
 public static final String ISREGISTER="isRegister";
 /**
  * 是否已经 绑定.
  */
 public static final String ISBIND="isBind";
 /**
  * 注册日期.
  */
 public static final String REGTIME="regTime";
 /**
  * 投资风险 .
  */
 public static final String FUNDSTYLE="fundStyle";
 /**
  * 总行量.
  */
 public static final String TOTALCOUNT="totalCount";
 /**
  * 累计总收益.
  */
 
 public static final String  TOTALDIVIDENDINCOME ="totalDividendIncome";
 /**
  * 最近扣款日期.
  */
 public static final String   LASTDEDUCTIONDATE   ="lastDeductionDate";
 
 /**
  * 下次扣款日期.
  */
 public static final String   NEXTDEDUCTIONDATE   ="nextDeductionDate";
 /**
  * 销售机构电话.
  */
 public static final String PHONE = "phone";
 /**
  * 分红单位.
  */
 public static final String DRAWBONUSUNIT = "drawBonusUnit";
  
}
