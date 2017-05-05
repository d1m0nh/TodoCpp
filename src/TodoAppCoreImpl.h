//
// Created by Dmitri Hammernik on 03.04.17.
//

#ifndef TODOCPP_APPTODOIMPL_H
#define TODOCPP_APPTODOIMPL_H

#pragma once
#include <memory>
#include <todo_app_core.hpp>
#include "TodoManagerImpl.h"

using namespace libtodo_gen;

namespace libtodo {
    class TodoAppCoreImpl : public TodoAppCore {
    private:
        const std::shared_ptr<Logger> logger;
        std::shared_ptr<TodoManagerImpl> todoManager;

    public:
        TodoAppCoreImpl(const std::string & root_path,
                    const std::shared_ptr<Logger> &logger);

        virtual void start() override;

        virtual void stop() override;

        virtual std::shared_ptr<TodoManager> get_todo_manager() override;
    };
}

#endif //TODOCPP_APPTODOIMPL_H
