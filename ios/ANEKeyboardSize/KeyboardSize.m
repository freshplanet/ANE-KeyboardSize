//////////////////////////////////////////////////////////////////////////////////////
//
//  Copyright 2012 Freshplanet (http://freshplanet.com | opensource@freshplanet.com)
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
//////////////////////////////////////////////////////////////////////////////////////

#import "KeyboardSize.h"


#pragma mark - KeyboardSize

DEFINE_ANE_FUNCTION(getKeyboardY)
{
    return NULL;
}

DEFINE_ANE_FUNCTION(getKeyboardHeight)
{
    return NULL;
}

DEFINE_ANE_FUNCTION(setKeyboardAdjustNothing)
{
    return NULL;
}

#pragma mark - C interface

void KeyboardSizeContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctions, const FRENamedFunction** functionsToSet)
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

void KeyboardSizeContextFinalizer(FREContext ctx) { }

void KeyboardSizeInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, FREContextFinalizer* ctxFinalizerToSet)
{
    *extDataToSet = NULL;
    *ctxInitializerToSet = &KeyboardSizeContextInitializer;
}

void KeyboardSizeFinalizer(void *extData) { }
