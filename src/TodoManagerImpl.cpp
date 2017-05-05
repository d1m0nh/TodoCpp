//
// Created by Dmitri Hammernik on 03.04.17.
//

#include "TodoManagerImpl.h"

using namespace libtodo;

namespace {
const string s_insert_stmt = "INSERT INTO `todo_manager` (`todo_title`, `todo_category`, `todo_state`) VALUES (?1, ?2, ?3);";
const string s_select_stmt = "SELECT * FROM `todo_manager`";
const string s_select_category_stmt = "SELECT * FROM `todo_manager` WHERE `todo_category` = ?1";
const string s_delete_stmt = "DELETE FROM `todo_manager` WHERE `id` = ?1";
}

TodoManagerImpl::TodoManagerImpl(const std::shared_ptr<sqlite::Db> db, std::shared_ptr<Logger> logger)
        : database(db)
        , logger(logger)
{
    initialize_db();
}

void TodoManagerImpl::add_todo(const Todo &todo) {
    this->logger->d("TodoManagerImpl", "Called add_todo.");
    auto insert_stmt = database->prepare(s_insert_stmt);

    sqlite::TransactionStmts transaction_stmts {database};
    sqlite::WriteTransaction guard {transaction_stmts};

    insert_stmt->reset();
    insert_stmt->bind(1, todo.todo_title);
    insert_stmt->bind(2, todo.todo_category);
    insert_stmt->bind(3, todo.todo_state);
    insert_stmt->exec();

    guard.commit();
}

std::experimental::optional<std::vector<Todo>> TodoManagerImpl::get_todos() {
    this->logger->d("TodoManagerImpl", "Called get_todos.");
    auto select_stmt = database->prepare(s_select_stmt);
    auto rows = select_stmt->exec_query().all_rows();

    auto res = std::vector<Todo>();
    for (auto &&row : rows) {
        auto todo = Todo{
                row[1].string_value(),
                row[2].string_value(),
                row[3].int_value(),
                row[0].int_value(),
        };

        res.push_back(todo);
    }

    return res;
}

void TodoManagerImpl::remove_todo(int32_t id) {
    this->logger->d("TodoManagerImpl", "Called remove_todo.");
    auto delete_stmt = database->prepare(s_delete_stmt);
    delete_stmt->bind(1, id);
    delete_stmt->exec();
}

std::experimental::optional<std::vector<Todo>>
TodoManagerImpl::get_todos_by_category(const std::string &category) {
    this->logger->d("TodoManagerImpl", "Called get_todos_by_category.");
    if(category == "All") {
        return this->get_todos();
    }

    auto select_stmt = database->prepare(s_select_category_stmt);
    select_stmt->bind(1, category);

    auto todos = std::vector<Todo>();
    auto rows = select_stmt->exec_query().all_rows();

    auto res = std::vector<Todo>();
    for (auto &&row : rows) {
        auto todo = Todo{
                row[1].string_value(),
                row[2].string_value(),
                row[3].int_value(),
                row[0].int_value(),
        };

        res.push_back(todo);
    }

    return res;
}

void TodoManagerImpl::initialize_db() {
    vector<string> setup_commands  {
            "CREATE TABLE IF NOT EXISTS `todo_manager` ("
                    "`id` INTEGER, "
                    "`todo_title` TEXT, "
                    "`todo_category` TEXT, "
                    "`todo_state` INTEGER, "
                    "PRIMARY KEY(id)"
                    ");"
    };

    for (const auto& cmd : setup_commands) {
        this->database->exec(cmd);
    }
}