package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;

public interface GetProductByIdUseCase {

    ProductResponse getProductById(String id);

}
