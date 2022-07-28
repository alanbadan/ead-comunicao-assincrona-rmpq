package com.ead.curso.enuns;
 //esse enum esat no msUsertb pq ele é quem vai publicar / aqui o curso esta escutando
public enum ActionType { //Enum para o Dto do UserEvent ele recebe uma String no dto pq se mudar o enum não precisa dar maniutenlção
                         
	CREATE,
	UPDATE,
	DELETE;
}
