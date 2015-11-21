package it.tryout.core.service;


import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.tryout.core.domain.bean.ValidationError;


public class ValidationErrorService {
	
	static final Logger logger = LogManager.getLogger(ValidationErrorService.class.getName());

	public static ValidationError makeValidationError(String field, String itemId, String errorMsg)
	{
		ValidationError ve = new ValidationError();
		ve.setErrorMsg(errorMsg);
		ve.setField(field);
		ve.setItemId(itemId);
		return ve;
	}
	
	public static ValidationError makeSystemError(String field, String itemId, String errorMsg)
	{
		ValidationError ve = new ValidationError();
		ve.setErrorMsg(errorMsg);
		ve.setField(field);
		ve.setItemId(itemId);
		StringWriter sw = new StringWriter();
		sw.append("Issue with ").append(itemId).append(" and field ").append(field).append(" with error message ").append(errorMsg);
		logger.warn(sw.toString());
		return ve;
	}
}
