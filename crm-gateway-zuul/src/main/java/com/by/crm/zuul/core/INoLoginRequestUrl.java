package com.by.crm.zuul.core;

import java.util.ArrayList;
import java.util.List;

public class INoLoginRequestUrl {
	public static final List<String> URL_LIST;
	static {
		URL_LIST = new ArrayList();
		URL_LIST.add("/loginForToken");
		URL_LIST.add("/loginOutForToken");
		URL_LIST.add("/verifyCodes");
		URL_LIST.add("/crm-weimall");
		URL_LIST.add("/sysconfig/wyy/message");
	}
}
