/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.cloud.gateway.swagger.model;

import lombok.Data;

@Data
public class GlobalOperationParameter {
	
	/**
	 * 参数名
	 **/
	private String name;

	/**
	 * 描述信息
	 **/
	private String description;
	
	/**
	 * 默认值
	 **/
	private String defaultValue;

	/**
	 * 指定参数类型
	 **/
	private String modelRef;

	/**
	 * 参数放在哪个地方:header,query,path,body.form
	 **/
	private String parameterType;

	/**
	 * 参数是否必须传
	 **/
	private boolean required;
	
	/**
	 * 参数是否隐藏
	 **/
	private boolean hidden;
	
	/**
	 * 参数格式
	 **/
	private String pattern;
  
	/**
	 * 参数是否允许为空
	 **/
	private boolean allowEmptyValue;

}