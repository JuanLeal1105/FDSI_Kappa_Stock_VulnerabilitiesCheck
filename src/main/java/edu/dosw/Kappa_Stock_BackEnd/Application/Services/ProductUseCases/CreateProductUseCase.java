package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.CreateProductCommand;

public interface CreateProductUseCase {

    ProductResponse createProduct(CreateProductCommand command);

}
