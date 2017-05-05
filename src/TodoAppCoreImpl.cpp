//
// Created by Dmitri Hammernik on 03.04.17.
//

#include "TodoAppCoreImpl.h"

using namespace libtodo;

std::shared_ptr<TodoAppCore> TodoAppCore::create_app(const std::string & root_path, const std::shared_ptr<Logger> & logger) {
    return std::make_shared<TodoAppCoreImpl>(root_path, logger);
}

TodoAppCoreImpl::TodoAppCoreImpl(const std::string &root_path, const std::shared_ptr<Logger> &logger)
    :logger(logger)
{
	this->logger->d("TodoAppCoreImpl", "Database file path " + root_path);
    auto db = libtodo::sqlite::Db::open(root_path + "/todo_manager.sqlite");
    todoManager = std::make_shared<TodoManagerImpl>(db, logger);
}

void TodoAppCoreImpl::start() {
    this->logger->d("TodoAppCoreImpl", "Called start.");
}

void TodoAppCoreImpl::stop() {
    this->logger->d("TodoAppCoreImpl", "Called stop.");
}

std::shared_ptr<TodoManager> TodoAppCoreImpl::get_todo_manager() {
    return todoManager;
}