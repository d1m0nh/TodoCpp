//
//  SecondViewController.swift
//  Todos
//
//  Created by Maik Hansen on 09.12.16.
//  Copyright © 2016 Maik Hansen. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource{

    /* create connections to ui elements */
    @IBOutlet weak var pickerView: UIPickerView!
    @IBOutlet weak var todoTitle: UITextField!
    @IBOutlet weak var textPicker: UITextField!
    @IBOutlet weak var add: UIButton!
    
    /* define category array */
    let pickerDataSource = ["Other", "Shopping", "Study", "Work", "Reminder"]
    var selectedCategory = ""
    
    private var todoManager: FHFLTodoManager!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        /* declare picker */
        let pickerView = UIPickerView()
        textPicker.inputView = pickerView
        
        self.todoManager = AppDelegate.getTodoAppCore().getTodoManager()!
        
        /* add bar to picker */
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.tintColor = UIColor(red: 76/255, green: 217/255, blue: 100/255, alpha: 1)
        toolBar.sizeToFit()
        
        /* add buttons */
        let doneButton = UIBarButtonItem(title: "Done", style: UIBarButtonItemStyle.plain, target: self, action: #selector(donePicker))
        let spaceButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.flexibleSpace, target: nil, action: nil)
        let cancelButton = UIBarButtonItem(title: "Cancel", style: UIBarButtonItemStyle.plain, target: self, action: #selector(donePicker))
        
        toolBar.setItems([cancelButton, spaceButton, doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        textPicker.inputAccessoryView = toolBar
        
        /* set source and delegates */
        pickerView.dataSource = self
        pickerView.delegate = self
       
        /* handle button */
        add.addTarget(self, action: #selector(addTodo(add:)), for: .touchUpInside)
        self.view.addSubview(add)
    }
    
    func donePicker (sender:UIBarButtonItem){
        textPicker.text = self.selectedCategory
        textPicker.resignFirstResponder()
    }
    
    /* button click event */
    func addTodo(add: UIButton) {
        /* here comes the db stuff */
        if(selectedCategory != "" && todoTitle.text != ""){
            let todo = FHFLTodo.init(todoTitle: todoTitle.text!, todoCategory: selectedCategory, todoState: 0, todoId: 123654)
            self.todoManager.add(todo)
            todoTitle.text = ""
            textPicker.text = ""
        }
    }
    
    /* UIPICKERVIEWDELEGATE PROTOCOL */
    /* number of components */
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    /* count of elements in source */
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickerDataSource.count
    }
    
    /* returned data in connection to the picker row */
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return pickerDataSource[row]
    }
    
    /* store selected value each time the user interacts */
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int){
        self.selectedCategory = pickerDataSource[row]
    }

}

