
package com.employee.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.employee.product.expensedetails.response.dto.ExpenseResDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/EProduct")
public class ExpenseController {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/expenseDetails")
	@ApiOperation(value = "expenseDetails")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Expense Details"),
	@ApiResponse(code = 401, message = "No Expense Details Exist"),
	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public ExpenseResDto retrieveExpenseDetails() {
		

		return null;
	}
}
