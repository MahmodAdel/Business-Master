#include <jni.h>


JNIEXPORT jstring JNICALL
Java_com_example_businessv1_di_NetworkModule_getNativeKey(JNIEnv *env, jobject thiz) {
    return (*env)->  NewStringUTF(env, "5kWjeDUo7fzXcanqAOQUlchIALfcbnvZ_eNFScrNybAxPrDoAqX06SkTMN2hvmjlXKWeN120l4--vX_RVUA9nShtpDgGqR0egHAFkCFyHADjXfri04_Lo94xqE6-X3Yx");

}