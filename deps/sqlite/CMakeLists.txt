cmake_minimum_required(VERSION 3.6)
project(sqlite3)

set(SOURCE_FILES sqlite3.h sqlite3ext.h)


# compile all *.cpp files
file (GLOB SOURCES "./*.c")

# define the sqlite header include file directory
set (SQLITE_INCLUDE sqlite3.h sqlite3ext.h)


# define the directories to search for headers
include_directories (include ${SQLITE_INCLUDE})

# Add the djinni support lib
if (APPLE)
    add_library(sqlite3 STATIC ${SOURCES})
endif(APPLE)

if (ANDROID)
    add_library(sqlite3 SHARED ${SOURCES})
endif(ANDROID)
