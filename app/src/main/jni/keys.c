#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_businessv1_MainActivity_getNativeKey(JNIEnv *env, jobject instance) {

    return (*env)->  NewStringUTF(env, "TmF0aXZlNWVjcmV0UEBzc3cwcmQx");
}
