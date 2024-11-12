/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.loadbalancer.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Nullable;

/**
 * Chooser.
 *
 * @author guer
 */
public class Chooser<K, T> {

	private final SecureRandom secureRandom = new SecureRandom();

	private final K uniqueKey;

	private volatile Ref<T> ref;

	public Chooser(K uniqueKey) {
		this(uniqueKey, new ArrayList<>());
	}

	public Chooser(K uniqueKey, List<Pair<T>> pairs) {
		this.uniqueKey = uniqueKey;
		this.ref = new Ref<>(pairs);
		ref.refresh();
	}

	/**
	 * Random get one item.
	 *
	 * @return item
	 */
	@Nullable
	public T random() {
		List<T> items = ref.getItems();
		if (items.isEmpty()) {
			return null;
		}
		if (items.size() == 1) {
			return items.get(0);
		}
		return items.get(secureRandom.nextInt(items.size()));
	}

	/**
	 * Random get one item with weight.
	 *
	 * @return item
	 */
	@Nullable
	public T randomWithWeight() {
		double random = secureRandom.nextDouble(0, 1);
		double[] weights = ref.getWeights();
		List<T> items = ref.getItems();
		int index = Arrays.binarySearch(weights, random);
		if (index < 0) {
			index = -index - 1;
		}
		else {
			return items.get(index);
		}

		if (index < weights.length && random < weights[index]) {
			return items.get(index);
		}

		/* This should never happen, but it ensures we will return a correct
		 * object in case there is some floating point inequality problem
		 * wrt the cumulative probabilities. */
		return items.get(items.size() - 1);
	}

	@Nullable
	public K getUniqueKey() {
		return uniqueKey;
	}

	@Nullable
	public Ref<T> getRef() {
		return ref;
	}

	/**
	 * refresh items.
	 *
	 * @param itemsWithWeight items with weight
	 */
	public void refresh(List<Pair<T>> itemsWithWeight) {
		Ref<T> newRef = new Ref<>(itemsWithWeight);
		newRef.refresh();
		newRef.setPoller(this.ref.getPoller().refresh(newRef.getItems()));
		this.ref = newRef;
	}

	@Override
	public int hashCode() {
		return uniqueKey.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (getClass() != other.getClass()) {
			return false;
		}

		Chooser<?, ?> otherChooser = (Chooser<?, ?>) other;
		if (this.uniqueKey == null) {
			if (otherChooser.getUniqueKey() != null) {
				return false;
			}
		}
		else {
			if (otherChooser.getUniqueKey() == null || !this.uniqueKey.equals(otherChooser.getUniqueKey())) {
				return false;
			}
		}

		if (this.ref == null) {
			return otherChooser.getRef() == null;
		}
		else {
			if (otherChooser.getRef() == null) {
				return false;
			}
			else {
				return this.ref.equals(otherChooser.getRef());
			}
		}
	}
}
