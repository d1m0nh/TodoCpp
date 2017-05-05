#!/bin/bash

base_dir=$(cd "`dirname "0"`" && pwd)/..
build_dir=$base_dir/build/ios
generated_src_dir=$base_dir/generated-src
lib_dir=$build_dir/lib
polly_dir=$base_dir/deps/polly

if [ ! -d "$generated_src_dir" ]; then
  ./run_djinni.sh
fi

if [ ! -d "$build_dir" ]; then
  mkdir -p $build_dir
fi

if [ ! -d "$lib_dir" ]; then
  mkdir -p $lib_dir
fi

echo -e "----------------------------------------------------------------"
echo -e "-- Running CMake for TodoCpp library (and dependencies) --"
echo -e "----------------------------------------------------------------\n"
cd $build_dir
cmake ../.. -GXcode -DCMAKE_TOOLCHAIN_FILE=$polly_dir/ios-10-0.cmake -DCMAKE_VERBOSE_MAKEFILE=ON;
echo -e ""

echo -e "--------------------------------------------------------"
echo -e "-- Compiling TodoCpp library (and dependencies) --"
echo -e "--------------------------------------------------------\n"
xcodebuild -target TodoCpp -configuration Release ENABLE_BITCODE=YES -arch arm64 -arch armv7 -arch armv7s only_active_arch=no defines_module=yes -sdk "iphoneos"
xcodebuild -target TodoCpp -configuration Debug GENERATE_Release_SYMBOLS=YES -arch x86_64 -arch i386 -arch only_active_arch=no defines_module=yes -sdk "iphonesimulator"
echo -e "Success\n"

echo -e "-------------------------------------------------"
echo -e "-- Creating TodoCpp.framework FAT binary --"
echo -e "-------------------------------------------------\n"
cp -R Release-iphoneos/TodoCpp.framework $lib_dir
lipo -create -output $lib_dir/TodoCpp.framework/TodoCpp Release-iphoneos/TodoCpp.framework/TodoCpp Debug-iphonesimulator/TodoCpp.framework/TodoCpp
echo -e "Success: \n" $lib_dir/TodoCpp.framework "\n"

echo -e "---------------------------------------------------"
echo -e "-- Validating TodoCpp.framework FAT binary --"
echo -e "---------------------------------------------------\n"
lipo -info $lib_dir/TodoCpp.framework/TodoCpp
echo -e ""

echo -e "-------------------------------------"
echo -e "-- Creating libdjinni.a FAT binary --"
echo -e "-------------------------------------\n"
cd deps/djinni
cp -R Release-iphoneos/libdjinni.a $lib_dir
lipo -create -output $lib_dir/libdjinni.a Release-iphoneos/libdjinni.a Release-iphonesimulator/libdjinni.a
echo -e "Success: \n" $lib_dir/djinni.dylib "\n"

echo -e "---------------------------------------"
echo -e "-- Validating libdjinni.a FAT binary --"
echo -e "---------------------------------------\n"
lipo -info $lib_dir/libdjinni.a
echo -e ""