cmake_minimum_required(VERSION 3.15)

project(native-lib)

find_package(JNI REQUIRED)

include_directories(${JNI_INCLUDE_DIRS})

add_library(native-lib SHARED native-lib.cpp native-lib.h)
