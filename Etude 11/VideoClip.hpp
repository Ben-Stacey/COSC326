//
// VideoClip.h
//
// Stub class for video clip
//
#ifndef VIDEOCLIP_H
#define VIDEOCLIP_H

#include <string>
//  Image class used for Video Clip
class Image;

class VideoClip {
public:
	VideoClip(){}

	VideoClip(std::string videoName){

	};

	~VideoClip(){
      delete previewImg;
	}


	Image* previewImg; //image for video preview
	int startFrame;
	int endFrame;

	float fps; //frames per second
	std::string videoFileName; // filename for reference
	std::string videoDescription; // description



};

#endif