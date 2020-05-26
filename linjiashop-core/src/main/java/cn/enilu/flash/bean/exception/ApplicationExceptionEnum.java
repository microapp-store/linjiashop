package cn.enilu.flash.bean.exception;

/**
 * 异常枚举
 *
 * @author enilu
 */
public enum ApplicationExceptionEnum implements ServiceExceptionEnum{

	/**
	 * 其他
	 */
	WRITE_ERROR(500,"渲染界面错误"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),

	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	MENU_PCODE_COINCIDENCE(400,"菜单编号和副编号不能一致"),

	USER_ALREADY_REG(401,"该用户已经注册"),
	CANT_CHANGE_ADMIN(600,"不能修改超级管理员角色"),
	/**
	 * 错误的请求
	 */
	REQUEST_NULL(400, "请求有错误"),
	DATA_ERROR(400, "数据异常"),
	EXISTED_THE_MENU(400,"菜单编号重复，不能添加"),

	DATA_CANNOT_REMOVE(500,"该数据存在关联数据，无法删除"),
	SERVER_ERROR(500, "服务异常，请联系管理员"),
	WECHAT_BIND_ANOTHER(500, "该手机号已经绑定其他微信号"),
	REQUEST_TOO_MANY(500, "请求频繁，请稍后重试"),
	TASK_CONFIG_ERROR(500, "定时任务配置错误");

	ApplicationExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
