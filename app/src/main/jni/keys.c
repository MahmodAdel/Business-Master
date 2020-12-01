#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_businessv1_frame_presentation_MainActivity_getNativeKey(JNIEnv *env,
                                                                         jobject thiz) {
    return (*env)->  NewStringUTF(env, "TmF0aXZlNWVjcmV0UEBzc3cwcmQx");
}