#指定cmake最低版本
cmake_minimum_required(VERSION 3.14)
#让cmake编译静态库校验编译器是否正常工作
set(CMAKE_TRY_COMPILE_TARGET_TYPE "STATIC_LIBRARY")

project(native-lib)

#判断目标平台
include(cmake/TargetArch.cmake)
target_architecture(ARCH)
message(${ARCH})
if (${ARCH} MATCHES "arm*")
    set(PLATFORM "linux_arm64")
    set(ENV{JAVA_HOME} "/home/ubuntu1804/tools/jdk8u212")
elseif (${ARCH} STREQUAL "i386")
    set(PLATFORM "windows_32")
elseif (${ARCH} STREQUAL "x86_64")
    set(PLATFORM "windows_64")
endif ()


set(CMAKE_MODULE_PATH /home/ubuntu1804/tools/cmake/cmake-3.14.5-Linux-x86_64/share/cmake-3.14/Modules)
#加入jni
find_package(JNI)
include_directories(${JNI_INCLUDE_DIRS})

#定义生成的库文件
add_library(native-lib SHARED native-lib.h native-lib.cpp)

#设置输出路径
if (${CMAKE_BUILD_TYPE} STREQUAL Release)
    SET(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/../resources/natives/${PLATFORM})
    message(LIBRARY_OUTPUT_PATH:${LIBRARY_OUTPUT_PATH})
endif ()



