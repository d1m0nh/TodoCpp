//
//  TodoManager.swift
//  Todos
//
//  Created by Maik Hansen on 27.12.16.
//  Copyright © 2016 Maik Hansen. All rights reserved.
//

import Foundation

class TodoManager {

    /* init function */
    init(){}
    
    /* get todos */
    public func managerGetTodos() -> Array<Todo> {
        return Database.instance.getTodos()
    }
    
    /* get filtered todos */
    public func managerGetTodos(category: String) -> Array<Todo> {
        return Database.instance.getFilteredTodos(todoCategory: category)
    }
    
    /* add todo */
    public func managerAddTodo(todoTitle: String, todoCategory: String){
        Database.instance.addTodo(todoTitle: todoTitle, todoCategory: todoCategory);
    }
    
    /* add todo */
    public func managerDeleteTodo(todoId: Int64){
        Database.instance.deleteTodo(todoId: todoId);
    }
}
