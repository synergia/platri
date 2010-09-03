/*
 *  oscserver.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-09-02.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <iostream>
#include "osc/OscOutboundPacketStream.h"
#include "ip/NetworkingUtils.h"
#include "ip/UdpSocket.h"




class OSCServer {
public:
	OSCServer(){
		initialize("127.0.0.1", 4444, 1500);
	}
	
	OSCServer(const char * host, int port){
		initialize(host, port, 1500);
	}
	
	void initialize(const char *host, int port, int size) {
		try {
			long unsigned int ip = GetHostByName(host);
			socket = new UdpTransmitSocket(IpEndpointName(ip, port));
			
			oscBuffer = new char[size];
			oscPacket = new osc::OutboundPacketStream(oscBuffer,size);
			fullBuffer = new char[size];
			fullPacket = new osc::OutboundPacketStream(fullBuffer,size);
		} catch (std::exception &e) { 
			std::cout << "could not create socket" << std::endl;
			socket = NULL;
		}
	}
	
	void send(){
		fullPacket->Clear();
		(*fullPacket) << osc::BeginBundleImmediate;
		
		(*fullPacket) << osc::BeginMessage("/platri") << "midi" << 1 << 5.5 << "dupa" << false;
		(*fullPacket) << osc::EndMessage;
		
		(*fullPacket) << osc::EndBundle;
		
		socket->Send(fullPacket->Data(), fullPacket->Size());
	}
	
protected:
	UdpTransmitSocket * socket;	
	osc::OutboundPacketStream  * oscPacket;
	char * oscBuffer; 
	osc::OutboundPacketStream  * fullPacket;
	char * fullBuffer; 
};

int main(int argc, const char * argv){
	OSCServer * server = new OSCServer("192.168.92.128", 4444);
	std::cout << "Sending message" << std::endl;
	server->send();
	return 0;
}