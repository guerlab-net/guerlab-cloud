/*
 * Copyright 2018-2024 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.searchparams.elasticsearch;

import lombok.Getter;
import lombok.Setter;

import net.guerlab.cloud.searchparams.BaseSearchParams;
import net.guerlab.cloud.searchparams.Column;
import net.guerlab.cloud.searchparams.SearchModel;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * @author guer
 */
@Setter
@Getter
@SuppressWarnings("unused")
public class TestSearchParams2 extends BaseSearchParams {

	@SearchModel(SearchModelType.EQUAL_TO)
	@Column(name = "inventories.vendorCode.abc")
	private String vendorCodeAbc;

	@SearchModel(SearchModelType.EQUAL_TO)
	@Column(name = "inventories.vendorCode")
	private String vendorCode;

	@SearchModel(SearchModelType.EQUAL_TO)
	@Column(name = "complexType")
	private String complexType;

	@SearchModel(SearchModelType.EQUAL_TO)
	@Column(name = "enable")
	private Boolean enable;
}
