//
//  ANEKeyboardSize.m
//  ANEKeyboardSize
//
//  Created by Li on 6/15/13.
//  Copyright (c) 2013 Li. All rights reserved.
//
#import "FlashRuntimeExtensions.h"

FREContext eventContext;

FREObject initFunction(FREContext ctx, void* functData, uint32_t argc, FREObject argv[])
{
    eventContext = ctx;
    
    return NULL;
}

FREObject getKeyboardY(FREContext ctx, void* functData, uint32_t argc, FREObject argv[])
{
    return NULL;
}

FREObject getKeyboardHeight(FREContext ctx, void* functData, uint32_t argc, FREObject argv[])
{
    return NULL;
}


FREObject setKeyboardAdjustNothing(FREContext ctx, void* functData, uint32_t argc, FREObject argv[])
{
    return NULL;
}

void ExtContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctions, const FRENamedFunction** functionsToSet)
{
    *numFunctions = 3;
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * *numFunctions);
    
    func[0].name = (const uint8_t*)"getKeyboardY";
    func[0].functionData = NULL;
    func[0].function = &getKeyboardY;
    
    func[1].name = (const uint8_t*)"getKeyboardHeight";
    func[1].functionData = NULL;
    func[1].function = &getKeyboardHeight;
    
    func[2].name = (const uint8_t*)"setKeyboardAdjustNothing";
    func[2].functionData = NULL;
    func[2].function = &setKeyboardAdjustNothing;
    
    *functionsToSet = func;
}

void KeyboardExtensionInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, FREContextFinalizer* ctxFinalizerToSet)
{
    *extDataToSet = NULL;
    *ctxInitializerToSet = &ExtContextInitializer;
}
