package com.ruoyi.util.id;

public class UUIDUtil {

	/**
	 * 获取特定的 uuid
	 * @return
	 */
	public static String getId() {
		return new UUIDHexGenerator().generate();
	}
	
	
}
