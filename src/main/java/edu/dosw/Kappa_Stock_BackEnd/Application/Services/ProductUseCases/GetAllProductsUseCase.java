package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;

import java.util.List;

public interface GetAllProductsUseCase {

    List<ProductResponse> getAllProducts();

}
