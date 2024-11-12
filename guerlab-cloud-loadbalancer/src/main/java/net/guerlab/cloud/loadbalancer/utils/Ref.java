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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Ref.
 *
 * @author guer
 */
@Setter
@Getter
public class Ref<T> {

	private final List<Pair<T>> itemsWithWeight;

	private final List<T> items = new ArrayList<>();

	private Poller<T> poller = new GenericPoller<>(items);

	private double[] weights;

	public Ref(List<Pair<T>> itemsWithWeight) {
		this.itemsWithWeight = itemsWithWeight;
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		double originWeightSum = 0;
		items.clear();

		for (Pair<T> item : itemsWithWeight) {
			double weight = item.weight();
			//ignore item which weight is zero.see test_randomWithWeight_weight0 in ChooserTest
			if (weight <= 0) {
				continue;
			}

			items.add(item.item());
			if (Double.isInfinite(weight)) {
				weight = 10000.0D;
			}
			if (Double.isNaN(weight)) {
				weight = 1.0D;
			}
			originWeightSum += weight;
		}

		if (items.isEmpty() || originWeightSum <= 0) {
			weights = new double[0];
			return;
		}

		double[] exactWeights = new double[items.size()];
		int index = 0;
		for (Pair<T> item : itemsWithWeight) {
			double singleWeight = item.weight();
			//ignore item which weight is zero.see test_randomWithWeight_weight0 in ChooserTest
			if (singleWeight <= 0) {
				continue;
			}
			exactWeights[index++] = singleWeight / originWeightSum;
		}

		weights = new double[items.size()];
		double randomRange = 0D;
		for (int i = 0; i < index; i++) {
			weights[i] = randomRange + exactWeights[i];
			randomRange += exactWeights[i];
		}

		double doublePrecisionDelta = 0.0001;

		if (index == 0 || (Math.abs(weights[index - 1] - 1) < doublePrecisionDelta)) {
			return;
		}
		throw new IllegalStateException(
				"Cumulative Weight calculate wrong , the sum of probabilities does not equals 1.");
	}

	@Override
	public int hashCode() {
		return itemsWithWeight.hashCode();
	}

	@SuppressWarnings({"unchecked"})
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
		Ref<T> otherRef = (Ref<T>) other;
		return this.itemsWithWeight.equals(otherRef.itemsWithWeight);
	}
}
