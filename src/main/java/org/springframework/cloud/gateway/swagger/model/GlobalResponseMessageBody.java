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

public class GlobalResponseMessageBody {

	/**
	 * 响应码
	 **/
	private String code;

	/**
	 * 响应消息
	 **/
	private String message;

	/**
	 * 响应体
	 **/
	private String modelRef;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getModelRef() {
		return modelRef;
	}

	public void setModelRef(String modelRef) {
		this.modelRef = modelRef;
	}

}