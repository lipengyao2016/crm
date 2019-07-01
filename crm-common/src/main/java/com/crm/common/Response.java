package com.crm.common;

import java.io.Serializable;

public interface Response extends Serializable {
	 String INSERT = "INSERT";
	 String UPDATE = "UPDATE";
	 String DELETE = "DELETE";
	 String SELECT = "SELECT";
}
