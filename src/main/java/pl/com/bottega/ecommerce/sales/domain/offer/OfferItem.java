/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class OfferItem {

	private ProductSnaphot productSnapshot;
	private int quantity;
	private Money totalCost;
	private Discount discount;

	public OfferItem(ProductSnaphot productSnapshot, int quantity, Discount discount) {
		this.productSnapshot = productSnapshot;
		this.quantity = quantity;
		this.discount = discount;

		BigDecimal discountValue = new BigDecimal(0);
		if (discount != null)
			discountValue = discountValue.subtract(discount.getDiscount().getValue());

		this.totalCost = new Money(productSnapshot.getProductPrice().getValue().
				multiply(new BigDecimal(quantity)).subtract(discountValue));
	}

	public OfferItem(ProductSnaphot productSnapshot, int quantity) {
		this(productSnapshot, quantity, null);
	}

	public ProductSnaphot getProductSnapshot() {
		return productSnapshot;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getTotalCost() {
		return totalCost;
	}

	public Discount getDiscount() {
		return discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((productSnapshot == null) ? 0 : productSnapshot.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfferItem other = (OfferItem) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (productSnapshot == null) {
			if (other.productSnapshot != null)
				return false;
		} else if (!productSnapshot.equals(other.productSnapshot))
			return false;
		if (quantity != other.quantity)
			return false;
		if (totalCost == null) {
			if (other.totalCost != null)
				return false;
		} else if (!totalCost.equals(other.totalCost))
			return false;
		return true;
	}

	/**
	 * 
	 * @param item
	 * @param delta
	 *            acceptable percentage difference
	 * @return
	 */
	public boolean sameAs(OfferItem other, double delta) {
		if (productSnapshot == null) {
			if (other.productSnapshot != null)
				return false;
		} else if (!productSnapshot.equals(other.productSnapshot))
			return false;

		if (quantity != other.quantity)
			return false;

		return totalCost.sameAs(other.totalCost, delta);
	}

}
