package edu.dosw.Kappa_Stock_BackEnd.Application.Services.ProductUseCases;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.ProductCommands.UpdateProductCommand;

public interface UpdateProductUseCase {
    ProductResponse updateProduct(UpdateProductCommand command);
}