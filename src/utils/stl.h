#pragma once
#include "stl_util.h"

#include <string>
using std::string;

#include <memory>
using std::unique_ptr;
using std::shared_ptr;
using std::make_shared;
using std::make_unique;
using std_patch::to_string;

#include <vector>
using std::vector;

#include <functional>
using std::function;

// less common stuff, don't "use" them
#include <mutex>
#include <condition_variable>

#include <experimental/optional>
using std::experimental::optional;
using std::experimental::nullopt;
using std::experimental::nullopt_t;