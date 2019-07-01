package com.crm.core;

import com.crm.common.ELoginChannel;
import com.crm.common.ELoginChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
	/**
	 * 获取client的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 获取客户端请求的系统环境
	 * 
	 * @return
	 */
	public static String getFromBy(HttpServletRequest request) {
		// Enumeration typestr = request.getHeaderNames();
		String userAgent = request.getHeader("user-agent");
		if (userAgent == null || "".equals(userAgent)) {
			return ELoginChannel.PC.name();
		}
		if (userAgent.toLowerCase().contains("ios") || userAgent.toLowerCase().contains("iphone")
				|| userAgent.toLowerCase().contains("ipad")) {
			return ELoginChannel.iOS.name();
		} else if (userAgent.toLowerCase().contains("android")) {
			return ELoginChannel.Android.name();
		} else if (userAgent.toLowerCase().contains("wap")) {
			return ELoginChannel.WAP.name();
		} else {
			return ELoginChannel.PC.name();
		}
	}
}
