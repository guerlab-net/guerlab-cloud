package net.guerlab.cloud.commons.util.test;

import lombok.Getter;
import lombok.Setter;
import net.guerlab.cloud.commons.entity.BaseOrderTreeEntity;
import net.guerlab.cloud.commons.entity.BaseTreeEntity;
import net.guerlab.cloud.commons.util.TreeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author guer
 */
public class TreeTest {

    @Test
    public void orderTreeEntity() {
        Collection<TestEntity1> entities = new ArrayList<>();
        entities.add(new TestEntity1(1L, 0L, 3));
        entities.add(new TestEntity1(2L, 0L, 2));
        entities.add(new TestEntity1(11L, 1L, 1));
        entities.add(new TestEntity1(12L, 1L, 2));
        entities.add(new TestEntity1(21L, 2L, 1));
        entities.add(new TestEntity1(22L, 2L, 2));

        List<TestEntity1> roots = TreeUtils.tree(entities);
        TestEntity1 root = roots.get(0);
        Assertions.assertNotNull(root);
        Assertions.assertTrue(root.getChildren() != null && !root.getChildren().isEmpty());
    }

    @Test
    public void treeEntity() {
        Collection<TestEntity2> entities = new ArrayList<>();
        entities.add(new TestEntity2(1L, 0L));
        entities.add(new TestEntity2(2L, 0L));
        entities.add(new TestEntity2(11L, 1L));
        entities.add(new TestEntity2(12L, 1L));
        entities.add(new TestEntity2(21L, 2L));
        entities.add(new TestEntity2(22L, 2L));

        List<TestEntity2> roots = TreeUtils.tree(entities);
        TestEntity2 root = roots.get(0);
        Assertions.assertNotNull(root);
        Assertions.assertTrue(root.getChildren() != null && !root.getChildren().isEmpty());
    }

    @Test
    public void empty() {
        Collection<TestEntity2> entities = new ArrayList<>();
        entities.add(new TestEntity2(1L, 0L));
        entities.add(new TestEntity2(2L, 0L));
        entities.add(new TestEntity2(11L, 1L));
        entities.add(new TestEntity2(12L, 1L));
        entities.add(new TestEntity2(21L, 2L));
        entities.add(new TestEntity2(22L, 2L));

        List<TestEntity2> roots = TreeUtils.tree(entities, 3L);
        Assertions.assertTrue(roots.isEmpty());
    }

    @Test
    public void empty2() {
        List<TestEntity2> roots = TreeUtils.tree(new ArrayList<TestEntity2>(), 3L);
        Assertions.assertTrue(roots.isEmpty());
    }

    @Setter
    @Getter
    public static class TestEntity1 extends BaseOrderTreeEntity<TestEntity1> {

        private Long id;

        private Long parentId;

        public TestEntity1(Long id, Long parentId, int orderNum) {
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
    }

    @Setter
    @Getter
    public static class TestEntity2 extends BaseTreeEntity<TestEntity2> {

        private Long id;

        private Long parentId;

        public TestEntity2(Long id, Long parentId) {
            this.id = id;
            this.parentId = parentId;
        }

        @Override
        public Long id() {
            return id;
        }

        @Override
        public Long parentId() {
            return parentId;
        }
    }
}
