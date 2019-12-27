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

/**
 * securitySchemes 支持方式之一 ApiKey
 */
public class Authorization {

	/**
	 * 鉴权策略ID，对应 SecurityReferences ID
	 */
	private String name = "X-Authorization";

	/**
	 * 鉴权策略，可选 ApiKey | BasicAuth | None，默认ApiKey
	 */
	private AuthorizationTypeEnum type = AuthorizationTypeEnum.APIKEY;

	/**
	 * 鉴权传递的Header参数
	 */
	private String keyName = "token";

	/**
	 * 需要开启鉴权URL的正则
	 */
	private String authRegex = "^.*$";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public AuthorizationTypeEnum getType() {
		return type;
	}

	public void setType(AuthorizationTypeEnum type) {
		this.type = type;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getAuthRegex() {
		return authRegex;
	}

	public void setAuthRegex(String authRegex) {
		this.authRegex = authRegex;
	}

}