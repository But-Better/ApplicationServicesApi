package com.butbetter.applicationservices.applicationservicesapi.service;

import com.butbetter.applicationservices.caluapi.service.CalculatorOperations;
import com.butbetter.applicationservices.productapi.model.Alcohol;
import com.butbetter.applicationservices.productapi.repository.AlcoholOperations;
import com.butbetter.applicationservices.storagerestapi.model.ProductInformation;
import com.butbetter.applicationservices.storagerestapi.repository.StorageOperation;

public interface ApplicationOperations extends AlcoholOperations<Alcohol>, StorageOperation<ProductInformation>, CalculatorOperations {
}
