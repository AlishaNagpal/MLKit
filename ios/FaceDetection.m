//
//  FaceDetection.m
//  MLKit
//
//  Created by Alisha Nagpal on 13/02/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
@interface RCT_EXTERN_MODULE(FaceDetection, NSObject)

RCT_EXTERN_METHOD(getSourceImage:(NSDictionary*)trackinfo callback:(RCTResponseSenderBlock))
@end
