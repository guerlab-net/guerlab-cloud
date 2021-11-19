/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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

package net.guerlab.cloud.searchparams.mybatisplus;

import lombok.Getter;
import lombok.Setter;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import net.guerlab.cloud.searchparams.SearchModel;
import net.guerlab.cloud.searchparams.SearchModelType;

import java.util.Collection;

/**
 * @author guer
 */
@Setter
@Getter
@SuppressWarnings("unused")
public class TestSearchParams extends AbstractSearchParams {

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ?")
    private Collection<String> t1;

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ?*")
    private Collection<String> t2;

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ? and 'column' in ?")
    private Collection<String> t3;

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ? and 'column' in ?*")
    private Collection<String> t4;

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ?* and 'column' in ?*")
    private Collection<String> t5;
}
