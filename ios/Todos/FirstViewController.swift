//
//  FirstViewController.swift
//  Todos
//
//  Created by Maik Hansen on 09.12.16.
//  Copyright © 2016 Maik Hansen. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    /* connect ui elements */
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var filter: UIButton!

    /* todo handling */
    private var todos = [FHFLTodo]()
    private var selectedTodo: Int?
    private var todoManager: FHFLTodoManager!
    
    /* load */
    override func viewDidLoad() {
        super.viewDidLoad()
        
        /* define delegate and source */
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
        self.todoManager = AppDelegate.getTodoAppCore().getTodoManager()
        
        /* get todos */
        self.todos = self.todoManager.getTodos()!
        
        /* handle button */
        filter.addTarget(self, action: #selector(filterTodo(filter:)), for: .touchUpInside)
        self.view.addSubview(filter)
    }
    
    /* reload list on appearing */
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.allTodos()
    }
    
    /* table view setter */
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedTodo = indexPath.row
        let id = todos[indexPath.row].todoId
        
        if selectedTodo != nil {
            /* change checked state */
            var checkbox: UIButton
    
            let cell = tableView.cellForRow(at: indexPath)
            checkbox = cell?.viewWithTag(3) as! UIButton
            checkbox.setBackgroundImage(UIImage(named: "taskFill.pdf"), for: .normal)
            
            /* execute delete after one second */
            let deadlineTime = DispatchTime.now() + .seconds(1)
            DispatchQueue.main.asyncAfter(deadline: deadlineTime, execute: {
                self.todoManager.removeTodo(id)
                checkbox.setBackgroundImage(UIImage(named: "taskEmpty.pdf"), for: .normal)
                self.allTodos()
            })
            
        } else {
            print("No item selected")
        }
    }
    
    /* return todos count */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return todos.count
    }
    
    /* cell height */
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat{
        return 70.0;
    }
    
    /* return the cell */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "TodoCell", for: indexPath)
        var label: UILabel
        
        label = cell.viewWithTag(1) as! UILabel
        label.text = self.todos[indexPath.row].todoTitle
        
        label = cell.viewWithTag(2) as! UILabel
        label.text = self.todos[indexPath.row].todoCategory
        
        return cell
    }
    
    /* button click event for filter */
    func filterTodo(filter: UIButton) {
      
        /* actionSheet */
        let alertController: UIAlertController = UIAlertController(title: "Filter Action", message: "Filter todos by category", preferredStyle: .actionSheet)
        
        let resetButton = UIAlertAction(title: "Reset", style: .default, handler: { (action) -> Void in
            self.allTodos()
        })
        
        let otherButton = UIAlertAction(title: "Other", style: .default, handler: { (action) -> Void in
            self.filterTodos(category: "Other")
        })
        
        let shoppingButton = UIAlertAction(title: "Shopping", style: .default, handler: { (action) -> Void in
            self.filterTodos(category: "Shopping")
        })
        
        let studyButton = UIAlertAction(title: "Study", style: .default, handler: { (action) -> Void in
            self.filterTodos(category: "Study")
        })
        
        let workButton = UIAlertAction(title: "Work", style: .default, handler: { (action) -> Void in
            self.filterTodos(category: "Work")
        })
        
        let reminderButton = UIAlertAction(title: "Reminder", style: .default, handler: { (action) -> Void in
            self.filterTodos(category: "Reminder")
        })

        let cancelButton = UIAlertAction(title: "Cancel", style: .cancel, handler: { (action) -> Void in })
        
        alertController.addAction(resetButton)
        alertController.addAction(otherButton)
        alertController.addAction(shoppingButton)
        alertController.addAction(studyButton)
        alertController.addAction(workButton)
        alertController.addAction(reminderButton)
        alertController.addAction(cancelButton)
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    /* filtered todos */
    private func filterTodos(category: String){
        self.todos = self.todoManager.getTodosByCategory(category)!
        self.tableView.reloadData()
    }
    
    /* all todos */
    private func allTodos(){
        self.todos = self.todoManager.getTodos()!
        self.tableView.reloadData()
    }
}

