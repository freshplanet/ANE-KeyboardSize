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

FREContext flashContext = nil;

@implementation KeyboardSize


+ (void)load
{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(keyboardWasShown:)
                                            name:UIKeyboardDidShowNotification
                                            object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(keyboardWasHidden:)
                                            name:UIKeyboardDidHideNotification
                                            object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillChange:)
                                                 name:UIKeyboardWillChangeFrameNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(keyboardChanged:)
                                            name:UIKeyboardDidChangeFrameNotification
                                            object:nil];
}

+ (void)keyboardWillShow:(NSNotification *)notification
{
    [KeyboardSize sendKeyboardEvent:@"KEYBOARD_WILL_SHOW" fromNotif:notification];
}


+ (void)keyboardWasShown:(NSNotification *)notification
{
    [KeyboardSize sendKeyboardEvent:@"KEYBOARD_DID_SHOW" fromNotif:notification];
}

+ (void)keyboardWillChange:(NSNotification *)notification
{
    [KeyboardSize sendKeyboardEvent:@"KEYBOARD_WILL_CHANGE" fromNotif:notification];
}


+ (void)keyboardChanged: (NSNotification *)notification
{
    [KeyboardSize sendKeyboardEvent:@"KEYBOARD_DID_CHANGE" fromNotif:notification];
}

+ (void) sendKeyboardEvent:(NSString *)name fromNotif:(NSNotification *)notification
{
    if(flashContext == nil) {
        return;
    }
    
    CGRect    screenRect;
    CGRect    windowRect;
    CGRect    viewRect;
    
    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
    UIView *topView = window.rootViewController.view;
    
    screenRect    = [[[notification userInfo] objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue];
    windowRect    = [window convertRect:screenRect fromWindow:nil];
    viewRect      = [topView convertRect:windowRect fromView:nil];
    
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    
    NSMutableDictionary *windowDict = [[NSMutableDictionary alloc] init];
    [windowDict setObject:[NSNumber numberWithFloat:screenRect.origin.x] forKey:@"x"];
    [windowDict setObject:[NSNumber numberWithFloat:screenRect.origin.y] forKey:@"y"];
    [windowDict setObject:[NSNumber numberWithFloat:screenRect.size.width] forKey:@"width"];
    [windowDict setObject:[NSNumber numberWithFloat:screenRect.size.height] forKey:@"height"];
    [dict setObject:windowDict forKey:@"windowRect"];
    
    NSMutableDictionary *viewDict = [[NSMutableDictionary alloc] init];
    [viewDict setObject:[NSNumber numberWithFloat:viewRect.origin.x] forKey:@"x"];
    [viewDict setObject:[NSNumber numberWithFloat:viewRect.origin.y] forKey:@"y"];
    [viewDict setObject:[NSNumber numberWithFloat:viewRect.size.width] forKey:@"width"];
    [viewDict setObject:[NSNumber numberWithFloat:viewRect.size.height] forKey:@"height"];
    [dict setObject:viewDict forKey:@"viewRect"];
    
    NSMutableDictionary *screenDict = [[NSMutableDictionary alloc] init];
    [screenDict setObject:[NSNumber numberWithFloat:windowRect.origin.x] forKey:@"x"];
    [screenDict setObject:[NSNumber numberWithFloat:windowRect.origin.y] forKey:@"y"];
    [screenDict setObject:[NSNumber numberWithFloat:windowRect.size.width] forKey:@"width"];
    [screenDict setObject:[NSNumber numberWithFloat:windowRect.size.height] forKey:@"height"];
    [dict setObject:screenDict forKey:@"screenRect"];
    NSNumber *scale = [NSNumber numberWithFloat:[[UIScreen mainScreen] nativeScale]];
    [dict setObject:scale forKey:@"nativeScale"];
    
    NSError *writeError = nil;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dict options:nil error:&writeError];
    NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    
    FREDispatchStatusEventAsync( flashContext, (const uint8_t*)[name UTF8String], (const uint8_t*)[jsonString UTF8String] );

}

+ (void)keyboardWillHide:(NSNotification *)notification
{
    if(flashContext != nil) {
        FREDispatchStatusEventAsync( flashContext, (const uint8_t*)"KEYBOARD_WILL_HIDE", (const uint8_t*)"" );
    }
}

+ (void)keyboardWasHidden:(NSNotification *)notification
{
    if(flashContext != nil) {
        FREDispatchStatusEventAsync( flashContext, (const uint8_t*)"KEYBOARD_DID_HIDE", (const uint8_t*)"" );
    }
}

@end



#pragma mark - KeyboardSize

DEFINE_ANE_FUNCTION(init)
{
    return NULL;
}

DEFINE_ANE_FUNCTION(getKeyboardY)
{
    return NULL;
}

DEFINE_ANE_FUNCTION(getKeyboardHeight)
{
    return NULL;
}

DEFINE_ANE_FUNCTION(setSoftInputMode)
{
    return NULL;
}

DEFINE_ANE_FUNCTION(removeClearButtonForiOS)
{
    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
    UIView *topView = window.rootViewController.view;
    
    setClearButtonMode(topView);
    return NULL;
}

DEFINE_ANE_FUNCTION(getMultilineTextViewHeight)
{
    FREObject ret = NULL;
    
    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
    UIView *topView = window.rootViewController.view;
    
    double height ;
    height = getTextViewHeight(topView);
    
    FRENewObjectFromDouble(height, &ret);
    return ret;
}




void setClearButtonMode(UIView *view)
{
    NSArray *subviews = [view subviews];
    
    // Return if there are no subviews
    if ([subviews count] == 0) return;
    
    for (UIView *subview in subviews) {
        
        //NSLog(@"%@", subview);
        if ([subview isKindOfClass:[UITextField class]])
        {
            //NSLog(@"TextField found");
            UITextField* textField= (UITextField*) subview;
            textField.clearButtonMode=UITextFieldViewModeNever;
        }
        setClearButtonMode(subview);
    }
}

double getTextViewHeight(UIView *view)
{
   
    NSArray *subviews = [view subviews];
    for (UIView *subview in subviews) {
        if ([subview isKindOfClass:[UITextView class]])
        {
            UITextView* textView= (UITextView*) subview;
            CGFloat fixedWidth = textView.frame.size.width;
            CGSize newSize = [textView sizeThatFits:CGSizeMake(fixedWidth, MAXFLOAT)];
            UIWindow *window = [[UIApplication sharedApplication] keyWindow];
            UIView *topView = window.rootViewController.view;
            CGFloat scale = [topView contentScaleFactor];
            return newSize.height * scale;
        } else {
            double textViewHeight = getTextViewHeight(subview);
            if(textViewHeight != 0) {
                return textViewHeight;
            }
        }
    }
    return 0;
}


#pragma mark - C interface

void KeyboardSizeContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctions, const FRENamedFunction** functionsToSet)
{
    flashContext = ctx;
    
    *numFunctions = 6;
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * *numFunctions);
    
    func[0].name = (const uint8_t*)"getKeyboardY";
    func[0].functionData = NULL;
    func[0].function = &getKeyboardY;
    
    func[1].name = (const uint8_t*)"getKeyboardHeight";
    func[1].functionData = NULL;
    func[1].function = &getKeyboardHeight;
    
    func[2].name = (const uint8_t*)"setSoftInputMode";
    func[2].functionData = NULL;
    func[2].function = &setSoftInputMode;
    
    func[3].name = (const uint8_t*)"removeClearButtonForiOS";
    func[3].functionData = NULL;
    func[3].function = &removeClearButtonForiOS;
    
    func[4].name = (const uint8_t*)"getMultilineTextViewHeight";
    func[4].functionData = NULL;
    func[4].function = &getMultilineTextViewHeight;
    
    func[5].name = (const uint8_t*)"init";
    func[5].functionData = NULL;
    func[5].function = &getMultilineTextViewHeight;
    
    
    *functionsToSet = func;
}

void KeyboardSizeContextFinalizer(FREContext ctx)
{
    flashContext = nil;
}

void KeyboardSizeInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, FREContextFinalizer* ctxFinalizerToSet)
{
    *extDataToSet = NULL;
    *ctxInitializerToSet = &KeyboardSizeContextInitializer;
}

void KeyboardSizeFinalizer(void *extData) { }
