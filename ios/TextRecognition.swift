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

@objc(TextRecognition)
class TextRecognition: NSObject {
  let vision = Vision.vision()
  
  @objc func getSourceImage(_ trackinfo: NSDictionary,callback: @escaping RCTResponseSenderBlock) -> Void{
  guard let infoDictionary = trackinfo as? [String: Any] else {return}
    
    load(fileName: infoDictionary["imageSource"] as! String,completion:{ result in
        
      let image = VisionImage(image: result)
      let textRecognizer = self.vision.onDeviceTextRecognizer()
//      let options = VisionCloudDocumentTextRecognizerOptions()
//      options.languageHints = ["en", "hi"]
//      let Recognizer = self.vision.cloudDocumentTextRecognizer(options: options)
      
      textRecognizer.process(image){ result, error in
      guard error == nil, let myText = result else {return}
        // Recognized text
        let resultText = myText.text
        //        for block in myText.blocks {
        //            let blockText = block.text
        //            let blockConfidence = block.confidence
        //            let blockLanguages = block.recognizedLanguages
        //            let blockCornerPoints = block.cornerPoints
        //            let blockFrame = block.frame
        //            for line in block.lines {
        //                let lineText = line.text
        //                let lineConfidence = line.confidence
        //                let lineLanguages = line.recognizedLanguages
        //                let lineCornerPoints = line.cornerPoints
        //                let lineFrame = line.frame
        //                for element in line.elements {
        //                    let elementText = element.text
        //                    let elementConfidence = element.confidence
        //                    let elementLanguages = element.recognizedLanguages
        //                    let elementCornerPoints = element.cornerPoints
        //                    let elementFrame = element.frame
        //                }
        //            }
        //        }
        
        let seconds = 0.1;
        DispatchQueue.main.asyncAfter(deadline: .now() + seconds) {
          callback([resultText])
          print(resultText)
        }
      }
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
