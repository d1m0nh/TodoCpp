#!/bin/bash

base_dir=$(cd "`dirname "0"`" && pwd)/../
in="$base_dir/djinni/todo.djinni"
cpp_out="$base_dir/generated-src/cpp"
jni_out="$base_dir/generated-src/jni"
objc_out="$base_dir/generated-src/objc"
java_out="$base_dir/generated-src/java/com/fhflensburg/libtodo/djinni"
java_package="com.fhflensburg.libtodo.djinni"
objc_prefix="FHFL"

$base_dir/deps/djinni/src/run \
   --java-out $java_out \
   --java-package $java_package \
   --ident-java-field mFooBar \
    \
    --cpp-out $cpp_out \
    --cpp-namespace libtodo_gen \
    --cpp-optional-template std::experimental::optional \
    --cpp-optional-header "<experimental/optional>" \
    --ident-cpp-enum-type foo_bar \
    \
    --jni-out $jni_out \
    --ident-jni-class NativeFooBar \
    --ident-jni-file NativeFooBar \
    \
    --objc-out $objc_out \
    --objcpp-out $objc_out \
    --objc-type-prefix $objc_prefix \
    \
    --idl "$in"
