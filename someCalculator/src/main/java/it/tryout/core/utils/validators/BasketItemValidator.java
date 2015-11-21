package it.tryout.core.utils.validators;

import it.tryout.core.domain.bean.BasketItem;
import it.tryout.core.domain.bean.ValidationError;
import it.tryout.core.service.ValidationErrorService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/*
 * unnecessary, just a general mock
 */
public class BasketItemValidator {

	public static List<ValidationError> validateBean(Object target)
	{
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (target instanceof BasketItem)
		{
			BasketItem bean = (BasketItem) target;
			if (bean.getMatchedItem() == null)
			{
				ValidationError anError = ValidationErrorService.makeValidationError("matchedItem", "unknown", "basket item type unbound");
				errors.add(anError);
				return errors;
			}
			else
			{
				if (StringUtils.isEmpty(bean.getMatchedItem().getItemId()))
				{
					ValidationError anError = ValidationErrorService.makeValidationError("itemId", "unknown", "basket item unbound itemId");
					errors.add(anError);
					return errors;
				}
				else
				{
					
				}
			}

		}
		else
		{
			
		}
		return errors;
	}
}
