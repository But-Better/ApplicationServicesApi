package com.butbetter.applicationservices.service;

import com.butbetter.applicationservices.model.ProductInformation;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IStorageApiService {
	/**
	 * Get all ProductInformation from StorageAPI
	 *
	 * @return a list of {@link ProductInformation} or null
	 */
	List<ProductInformation> all();

	/**
	 * Get one ProductInformation from StorageAPI
	 *
	 * @param id = type as UUID
	 * @return {@link ProductInformation} or null
	 */
	ProductInformation one(@NotNull String id);

	/**
	 * Add a new ProductionInformation at StorageAPI
	 *
	 * @param productInformation = {@link ProductInformation}
	 */
	void newProductInformation(@NotNull ProductInformation productInformation);
}
