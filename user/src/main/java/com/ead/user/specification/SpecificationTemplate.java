package com.ead.user.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ead.user.model.UserModel;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate { // classe para especifiacr os filtros dos templates

	//interface pra usar os filtro do specification
//	@Or  // paga um ou outro( email ou status ...)
	@And({             //anotacao and faz combinacao.
	     @Spec(path ="userType", spec = Equal.class), // filtro usertype(enun) ,o tipo retornado o valor extao do filtro (nocaso Equal) 
	     @Spec(path ="userStatus", spec = Equal.class),
	     @Spec(path ="email", spec = Like.class), 
	     @Spec(path = "fullName", spec = Like.class) // perquisa pelo nome 
	})
	public interface UserSpec extends Specification<UserModel>{} // sepcfication do srpingJpa
		
}
