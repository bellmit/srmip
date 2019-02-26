package org.jeecgframework.web.system.pojo.base;

/**
 * 待办信息查询对象
 * 
 */
public class PortalUserVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 创建人名称 */
	private String createName;
	/** 创建人登录名称 */
	private String createBy;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 更新人名称 */
	private String updateName;
	/** 更新人登录名称 */
	private String updateBy;
	/** 更新日期 */
	private java.util.Date updateDate;
	/** 行号 */
	private Integer rowNo;
	/** 列号 */
	private Integer colNo;
	/** 用户账号 */
	private String userName;

	/**
	 * 待办id
	 */
	private String portalId;
	/**
	 * 高度
	 */
	private Integer height;

	/**
	 * 地址
	 */
	private String url;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 是否添加，0 未添加 ，1已添加
	 */
	private String status = "0";

	/**
	 * 待办数据.
	 */
	private String portalData;

	public PortalUserVo() {
	}

	public PortalUserVo(String id, String title, Integer height, String url,
			String userName, Integer rowNo, Integer colNo, String portalId) {
		super();
		this.id = id;
		this.rowNo = rowNo;
		this.colNo = colNo;
		this.userName = userName;
		this.height = height;
		this.url = url;
		this.title = title;
		this.portalId = portalId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	public Integer getColNo() {
		return colNo;
	}

	public void setColNo(Integer colNo) {
		this.colNo = colNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPortalData() {
		return portalData;
	}

	public void setPortalData(String portalData) {
		this.portalData = portalData;
	}

}
