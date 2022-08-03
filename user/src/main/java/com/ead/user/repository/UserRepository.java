package com.ead.user.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ead.user.model.UserModel;
                                                                        //por causa do spec
public interface UserRepository  extends JpaRepository<UserModel, UUID>,JpaSpecificationExecutor<UserModel>{

	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);
	
    //anotacao por causa do creegamento Lazy definido no UserModel
	//pq alem de carregar o nome deve-se carregar o coleção de roles por isso se define no attributePaths a colecao de recarregamento
	@EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
	Optional<UserModel> findByUserName(String username);
	
	 //anotacao por causa do creegamento Lazy definido no UserModel
		//pq alem de carregar o nome deve-se carregar o coleção de roles por isso se define no attributePaths a colecao de recarregamento
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
	Optional<UserModel> findById(UUID userId);
	
}
