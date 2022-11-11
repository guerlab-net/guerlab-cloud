/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.commons.entity.IOrderlyEntity;
import net.guerlab.cloud.commons.entity.TreeEntity;
import net.guerlab.cloud.commons.entity.TreeNode;

/**
 * @author guer
 */
class TreeUtilsTests {

	@Test
	void orderTreeEntity() {
		Collection<TestEntity1> entities = new ArrayList<>();
		entities.add(new TestEntity1(1L, 0L, 3));
		entities.add(new TestEntity1(2L, 0L, 2));
		entities.add(new TestEntity1(11L, 1L, 1));
		entities.add(new TestEntity1(12L, 1L, 2));
		entities.add(new TestEntity1(21L, 2L, 1));
		entities.add(new TestEntity1(22L, 2L, 2));

		List<TreeEntity<TestEntity1, Long>> roots = TreeUtils.tree(entities);
		TreeEntity<TestEntity1, Long> root = roots.get(0);
		Assertions.assertNotNull(root);
		Assertions.assertTrue(root.getChildren() != null && !root.getChildren().isEmpty());
		Assertions.assertEquals(3, root.getObject().getOrderNum());
	}

	@Test
	void treeEntity() {
		Collection<TestEntity2> entities = new ArrayList<>();
		entities.add(new TestEntity2(1L, 0L));
		entities.add(new TestEntity2(2L, 0L));
		entities.add(new TestEntity2(11L, 1L));
		entities.add(new TestEntity2(12L, 1L));
		entities.add(new TestEntity2(21L, 2L));
		entities.add(new TestEntity2(22L, 2L));

		List<TreeEntity<TestEntity2, String>> roots = TreeUtils.tree(entities, "0");
		TreeEntity<TestEntity2, String> root = roots.get(0);
		Assertions.assertNotNull(root);
		Assertions.assertTrue(root.getChildren() != null && !root.getChildren().isEmpty());
	}

	@Test
	void empty() {
		Collection<TestEntity2> entities = new ArrayList<>();
		entities.add(new TestEntity2(1L, 0L));
		entities.add(new TestEntity2(2L, 0L));
		entities.add(new TestEntity2(11L, 1L));
		entities.add(new TestEntity2(12L, 1L));
		entities.add(new TestEntity2(21L, 2L));
		entities.add(new TestEntity2(22L, 2L));

		List<TreeEntity<TestEntity2, String>> roots = TreeUtils.tree(entities, "3");
		Assertions.assertTrue(roots.isEmpty());
	}

	@Test
	void empty2() {
		Collection<TestEntity2> entities = new ArrayList<>();
		List<TreeEntity<TestEntity2, String>> roots = TreeUtils.tree(entities, "3");
		Assertions.assertTrue(roots.isEmpty());
	}

	@Data
	public static class TestEntity1 implements TreeNode<Long>, IOrderlyEntity<TestEntity1> {

		private Long id;

		private Long parentId;

		private Integer orderNum;

		TestEntity1(Long id, Long parentId, int orderNum) {
			this.id = id;
			this.parentId = parentId;
			this.orderNum = orderNum;
		}

		@Override
		public Long id() {
			return id;
		}

		@Override
		public Long parentId() {
			return parentId;
		}

		@Override
		public String label() {
			return null;
		}
	}

	@Data
	public static class TestEntity2 implements TreeNode<String> {

		private String id;

		private String parentId;

		TestEntity2(Long id, Long parentId) {
			this.id = id.toString();
			this.parentId = parentId.toString();
		}

		@Override
		public String id() {
			return id;
		}

		@Override
		public String parentId() {
			return parentId;
		}

		@Override
		public String label() {
			return null;
		}
	}
}
