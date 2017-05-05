//
// Created by Dmitri Hammernik on 03.04.17.
//

#ifndef TODOCPP_TODOMANAGER_H
#define TODOCPP_TODOMANAGER_H

#pragma once
#include <string>
#include <memory>
#include <experimental/optional>
#include "logger.hpp"
#include "todo.hpp"
#include "todo_manager.hpp"
#include "sqlite/db.hpp"
#include "sqlite/sqlite.hpp"

using namespace libtodo_gen;

namespace libtodo {
    class TodoManagerImpl : public TodoManager {
    private:
        const std::shared_ptr<Logger> logger;
        std::shared_ptr<sqlite::Db> database;

        void initialize_db();

    public:
        TodoManagerImpl(const std::shared_ptr<sqlite::Db> db, std::shared_ptr<Logger> logger);

        virtual void add_todo(const Todo &todo) override;

        virtual std::experimental::optional<std::vector<Todo>> get_todos() override;

        virtual void remove_todo(int32_t id) override;

        virtual std::experimental::optional<std::vector<Todo>>
        get_todos_by_category(const std::string &category) override;
    };
}

#endif //TODOCPP_TODOMANAGER_H
