package edu.dosw.Kappa_Stock_BackEnd.Application.Services.StockUseCases;

import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.ProductResponse;
import edu.dosw.Kappa_Stock_BackEnd.Application.Dtos.command.StockCommands.StockOperationCommand;

public interface IncreaseStockUseCase {
    ProductResponse increaseStock(StockOperationCommand command);
}