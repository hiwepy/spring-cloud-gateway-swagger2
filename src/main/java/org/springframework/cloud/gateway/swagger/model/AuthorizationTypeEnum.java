/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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

import java.util.NoSuchElementException;

/**
 * 鉴权策略，可选 ApiKey | BasicAuth | None，默认ApiKey
 * @author ： <a href="https://github.com/vindell">wandl</a>
 */
public enum AuthorizationTypeEnum {

	APIKEY("ApiKey"), BASICAUTH("BasicAuth"), NONE("None");

	private final String type;

	AuthorizationTypeEnum(String type) {
		this.type = type;
	}

	public String get() {
		return type;
	}
	
	public boolean equals(AuthorizationTypeEnum type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String type){
		return this.compareTo(AuthorizationTypeEnum.valueOfIgnoreCase(type)) == 0;
	}
	
	public static AuthorizationTypeEnum valueOfIgnoreCase(String type) {
		for (AuthorizationTypeEnum transport : AuthorizationTypeEnum.values()) {
			if(transport.get().equalsIgnoreCase(type)) {
				return transport;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthorizationType with type '" + type + "'.");
    }
	
}