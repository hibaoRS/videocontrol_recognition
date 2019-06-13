//
// Created by 10324 on 2019/6/13.
//

#include "native-lib.h"


JNIEXPORT jstring JNICALL Java_com_xqdd_view_MainView_from_1cpp1(JNIEnv *env, jobject) {
    return env->NewStringUTF("Wow!!!");
}


JNIEXPORT jstring JNICALL Java_com_xqdd_view_MainView_from_1cpp2(JNIEnv *env, jobject) {
    return env->NewStringUTF("You've got it!!!");
}
