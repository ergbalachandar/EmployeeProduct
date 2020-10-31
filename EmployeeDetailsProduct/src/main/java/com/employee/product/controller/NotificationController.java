package com.employee.product.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.NotificationService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.notification.dto.NotificationDetails;
import com.employee.product.notification.dto.NotificationReq;
import com.employee.product.utils.NotificationUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/EProduct")
public class NotificationController {

	@Autowired
	NotificationService notifyService;

	/**
	 * Method to GetNotifications of an each login User
	 * 
	 * @param CompanyDetailsDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/notifications")
	@ApiOperation(value = "Getting Notifications", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Getting Notifications Based on User"),
			@ApiResponse(code = 401, message = "You are not authorized to recieve Notifications"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public NotificationDetails getNotifications(HttpSession httpSession) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT();
		NotificationDetails notifyDetails = new NotificationDetails();
		String empId = null;
		for (EmployeeDetails em : userDetails.getUsers().getEmployeeDetails()) {
			empId = em.getId();
		}
		if (null == empId) {
			throw new Exception("Error in retrieve notifications");
		}
		NotificationUtil.mapNotifications(notifyDetails, notifyService.pullNotification(empId));
		return notifyDetails;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateNotif")
	@ApiOperation(value = "Update Notifications", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updating Notification Based on User Readability"),
			@ApiResponse(code = 401, message = "You are not authorized to recieve Notifications"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public boolean updateNotifications(@RequestParam NotificationReq notifReq,HttpSession httpSession) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT();
		String empId = null;
		for (EmployeeDetails em : userDetails.getUsers().getEmployeeDetails()) {
			empId = em.getId();
		}
		if (null == empId) {
			throw new Exception("Error in retrieve notifications");
		}
		if(null == notifReq || null ==  notifReq.getId()) {
			throw new Exception("Error in updating notifications");
		}
		NotificationDetailsEntity ent = new NotificationDetailsEntity();
		NotificationUtil.updateNotify(ent,notifReq);
		notifyService.updateNotification(ent);
		return true;
		
	}

	private UserDetailsImpl generateUserDetailsFromJWT() throws Exception {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (userDetails.getUsers().getActive() == 0) {
			throw new Exception("Your profile has been deleted");
		}
		return userDetails;
	}
}
