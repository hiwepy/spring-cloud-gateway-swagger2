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

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GlobalResponseMessage {

	/**
	 * POST 响应消息体
	 **/
	List<GlobalResponseMessageBody> post = new ArrayList<>();

	/**
	 * GET 响应消息体
	 **/
	List<GlobalResponseMessageBody> get = new ArrayList<>();

	/**
	 * PUT 响应消息体
	 **/
	List<GlobalResponseMessageBody> put = new ArrayList<>();

	/**
	 * PATCH 响应消息体
	 **/
	List<GlobalResponseMessageBody> patch = new ArrayList<>();

	/**
	 * DELETE 响应消息体
	 **/
	List<GlobalResponseMessageBody> delete = new ArrayList<>();

	/**
	 * HEAD 响应消息体
	 **/
	List<GlobalResponseMessageBody> head = new ArrayList<>();

	/**
	 * OPTIONS 响应消息体
	 **/
	List<GlobalResponseMessageBody> options = new ArrayList<>();

	/**
	 * TRACE 响应消息体
	 **/
	List<GlobalResponseMessageBody> trace = new ArrayList<>();

}