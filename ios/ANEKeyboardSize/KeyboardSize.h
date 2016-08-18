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

#import "FlashRuntimeExtensions.h"
#import <UIKit/UIKit.h>

@interface KeyboardSize : NSObject
+ (void)load;
@end

DEFINE_ANE_FUNCTION(getKeyboardY);
DEFINE_ANE_FUNCTION(getKeyboardHeight);
DEFINE_ANE_FUNCTION(setSoftInputMode);
DEFINE_ANE_FUNCTION(removeClearButtonForiOS);
DEFINE_ANE_FUNCTION(getMultilineTextViewHeight);

void setClearButtonMode(UIView *view);
double getTextViewHeight(UIView *view);

// ANE Setup
void KeyboardSizeContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctionsToTest, const FRENamedFunction** functionsToSet);
void KeyboardSizeContextFinalizer(FREContext ctx);
void KeyboardSizeInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, FREContextFinalizer* ctxFinalizerToSet);
void KeyboardSizeFinalizer(void *extData);