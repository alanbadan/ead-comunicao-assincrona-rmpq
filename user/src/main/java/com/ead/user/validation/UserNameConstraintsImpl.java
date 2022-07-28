package com.ead.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//uma aboradagem mais simple para criacao de validacao customizada
public class UserNameConstraintsImpl implements ConstraintValidator<UserNameConstraints,String>{

	@Override
	public void initialize(UserNameConstraints constraintsAnotation) {
		ConstraintValidator.super.initialize(constraintsAnotation);
	}
	
	
	@Override // esse metodo é para validar se o userNmae tem espaço em branco 
	public boolean isValid(String userName, ConstraintValidatorContext context) {
	  if(userName == null || userName.trim().isEmpty() || userName.contains(" ")) {
		  return false;
	  }
		return true;
	} 
	
}
