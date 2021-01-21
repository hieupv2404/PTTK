package inventory.validate;

import inventory.model.Issue;
import inventory.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class IssueValidator implements Validator {
    @Autowired
    private IssueService issueService;

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz == Issue.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Issue issue = (Issue) target;
        ValidationUtils.rejectIfEmpty(errors, "customer.name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");

//		if (productInfo.getName() != null) {
//			List<ProductInfo> results = vatService.findProductInfo("name", productInfo.getName());
//			if (results != null && !results.isEmpty()) {
//				if (productInfo.getId() != null && productInfo.getId() != 0) {
//					if (results.get(0).getId() != productInfo.getId()) {
//						errors.rejectValue("name", "msg.name.exist");
//					}
//				} else {
//					errors.rejectValue("name", "msg.name.exist");
//				}
//
//			}
//		}
    }

}
