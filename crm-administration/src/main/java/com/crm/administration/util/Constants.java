package com.crm.administration.util;

public interface Constants {
	/**
	 * token
	 */
	public final static String INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX = "insurance:userinfo:token:{userLoginId}:{fromBy}:{token}";
	public final static String INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX_REGEX = "(.*)\\{userLoginId\\}(.*)\\{fromBy\\}(.*)\\{token\\}";

	/**
	 * user_tokens
	 */
	public final static String INSURANCE_USERINFO_TOKEN_XXX_XXX = "insurance:userinfo:token:{userLoginId}:{fromBy}:";
	public final static String INSURANCE_USERINFO_TOKEN_XXX_XXX_REGEX = "(.*)\\{userLoginId\\}(.*)\\{fromBy\\}(.*)";

}
