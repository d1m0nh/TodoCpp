//
//  Logger.swift
//  Todos
//
//  Created by Dmitri Hammernik on 05.04.17.
//  Copyright © 2017 Maik Hansen. All rights reserved.
//

import Foundation

class Logger : FHFLLogger {
    func d(_ tag: String, message: String) {
        print("DEBUG", tag, message)
    }
    
    func i(_ tag: String, message: String) {
        print("INFO", tag, message)
    }
    
    func e(_ tag: String, error: String) {
        print("ERROR", tag, error)
    }
}
