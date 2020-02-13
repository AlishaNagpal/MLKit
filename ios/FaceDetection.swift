//
//  TextRecognition.swift
//  MLKit
//
//  Created by Alisha Nagpal on 13/02/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
import UIKit
import Firebase

@objc(FaceDetection)
class FaceDetection: NSObject {
  lazy var vision = Vision.vision()
  
  @objc func getSourceImage(_ trackinfo: NSDictionary,callback: @escaping RCTResponseSenderBlock) -> Void{
  guard let infoDictionary = trackinfo as? [String: Any] else {return}
    let options = VisionFaceDetectorOptions()
    options.performanceMode = .accurate
    options.landmarkMode = .all
    options.classificationMode = .all
    let faceDetector = self.vision.faceDetector(options: options)
    
    load(fileName: infoDictionary["imageSource"] as! String,completion:{ result in
      let image = VisionImage(image: result)
      
      faceDetector.process(image) { faces, error in
        guard error == nil, let faces = faces, !faces.isEmpty else {return}
        
      }
//      let seconds = 0.1;
//      DispatchQueue.main.asyncAfter(deadline: .now() + seconds) {
//        callback([resultText])
//      }
      
        
      
    })
    
  }
  
  @objc
  func load(fileName: String, completion: @escaping (UIImage)->()) {
    DispatchQueue.global(qos: .background).async {
      do {
        let data = try Data.init(contentsOf: URL.init(string:fileName)!)
        DispatchQueue.main.async {
          completion(UIImage(data: data)!)
        }
      }
      catch {
        print("Not able to load image")
      }
    }
  }
  
  
  
}
