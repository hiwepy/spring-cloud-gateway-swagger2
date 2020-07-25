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

import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;

public class UiConfig {

    private String apiSorter = "alpha";

    /**
     * 是否启用json编辑器
     **/
    private Boolean jsonEditor = false;
    /**
     * 是否显示请求头信息
     **/
    private Boolean showRequestHeaders = true;
    /**
     * 支持页面提交的请求类型
     **/
    private String submitMethods = "get,post,put,delete,patch";
    /**
     * 请求超时时间
     **/
    private Long requestTimeout = 10000L;

    private Boolean deepLinking;
    private Boolean displayOperationId;
    private Integer defaultModelsExpandDepth;
    private Integer defaultModelExpandDepth;
    private ModelRendering defaultModelRendering;

    /**
     * 是否显示请求耗时，默认false
     */
    private Boolean displayRequestDuration = true;
    /**
     * 可选 none | list
     */
    private DocExpansion docExpansion;
    /**
     * Boolean=false OR String
     */
    private Object filter;
    private Integer maxDisplayedTags;
    private OperationsSorter operationsSorter;
    private Boolean showExtensions;
    private Boolean showCommonExtensions = false;
    private TagsSorter tagsSorter;

    /**
     * Network
     */
    private String validatorUrl;

	public String getApiSorter() {
		return apiSorter;
	}

	public void setApiSorter(String apiSorter) {
		this.apiSorter = apiSorter;
	}

	public Boolean getJsonEditor() {
		return jsonEditor;
	}

	public void setJsonEditor(Boolean jsonEditor) {
		this.jsonEditor = jsonEditor;
	}

	public Boolean getShowRequestHeaders() {
		return showRequestHeaders;
	}

	public void setShowRequestHeaders(Boolean showRequestHeaders) {
		this.showRequestHeaders = showRequestHeaders;
	}

	public String getSubmitMethods() {
		return submitMethods;
	}

	public void setSubmitMethods(String submitMethods) {
		this.submitMethods = submitMethods;
	}

	public Long getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(Long requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public Boolean getDeepLinking() {
		return deepLinking;
	}

	public void setDeepLinking(Boolean deepLinking) {
		this.deepLinking = deepLinking;
	}

	public Boolean getDisplayOperationId() {
		return displayOperationId;
	}

	public void setDisplayOperationId(Boolean displayOperationId) {
		this.displayOperationId = displayOperationId;
	}

	public Integer getDefaultModelsExpandDepth() {
		return defaultModelsExpandDepth;
	}

	public void setDefaultModelsExpandDepth(Integer defaultModelsExpandDepth) {
		this.defaultModelsExpandDepth = defaultModelsExpandDepth;
	}

	public Integer getDefaultModelExpandDepth() {
		return defaultModelExpandDepth;
	}

	public void setDefaultModelExpandDepth(Integer defaultModelExpandDepth) {
		this.defaultModelExpandDepth = defaultModelExpandDepth;
	}

	public ModelRendering getDefaultModelRendering() {
		return defaultModelRendering;
	}

	public void setDefaultModelRendering(ModelRendering defaultModelRendering) {
		this.defaultModelRendering = defaultModelRendering;
	}

	public Boolean getDisplayRequestDuration() {
		return displayRequestDuration;
	}

	public void setDisplayRequestDuration(Boolean displayRequestDuration) {
		this.displayRequestDuration = displayRequestDuration;
	}

	public DocExpansion getDocExpansion() {
		return docExpansion;
	}

	public void setDocExpansion(DocExpansion docExpansion) {
		this.docExpansion = docExpansion;
	}

	public Object getFilter() {
		return filter;
	}

	public void setFilter(Object filter) {
		this.filter = filter;
	}

	public Integer getMaxDisplayedTags() {
		return maxDisplayedTags;
	}

	public void setMaxDisplayedTags(Integer maxDisplayedTags) {
		this.maxDisplayedTags = maxDisplayedTags;
	}

	public OperationsSorter getOperationsSorter() {
		return operationsSorter;
	}

	public void setOperationsSorter(OperationsSorter operationsSorter) {
		this.operationsSorter = operationsSorter;
	}

	public Boolean getShowExtensions() {
		return showExtensions;
	}

	public void setShowExtensions(Boolean showExtensions) {
		this.showExtensions = showExtensions;
	}
	
	

	public Boolean getShowCommonExtensions() {
		return showCommonExtensions;
	}

	public void setShowCommonExtensions(Boolean showCommonExtensions) {
		this.showCommonExtensions = showCommonExtensions;
	}

	public TagsSorter getTagsSorter() {
		return tagsSorter;
	}

	public void setTagsSorter(TagsSorter tagsSorter) {
		this.tagsSorter = tagsSorter;
	}

	public String getValidatorUrl() {
		return validatorUrl;
	}

	public void setValidatorUrl(String validatorUrl) {
		this.validatorUrl = validatorUrl;
	}
    
    
}