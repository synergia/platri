/*
 *  midi.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-09-03.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "midi.h"
#include <stdio.h>

namespace midi {
	OSCProxy * osc = NULL;
}


void midi::init(const char * address, int port){
	osc = new OSCProxy(address, port);
}

void midi::send(int command, int channel, int data1, int data2){
	osc->send(command, channel, data1, data2);
}

void midi::note_on(int data1){
	note_on(data1, -1);
}

void midi::note_on(int data1, int data2){
	note_on(data1, data2, -1);
}

void midi::note_on(int data1, int data2, int channel){
	send(NOTE_ON, channel, data1, data2);
}

midi::OSCProxy::OSCProxy(){
	initialize("127.0.0.1", 4444, 1500);
}

midi::OSCProxy::OSCProxy(const char * host, int port){
	initialize(host, port, 1500);
}

void midi::OSCProxy::initialize(const char *host, int port, int size) {
	try {
		long unsigned int ip = GetHostByName(host);
		socket = new UdpTransmitSocket(IpEndpointName(ip, port));
		
		fullBuffer = new char[size];
		fullPacket = new osc::OutboundPacketStream(fullBuffer,size);
	} catch (std::exception &e) { 
		printf("[MIDI] Could not create socket\n");
		socket = NULL;
	}
}

// OSC Message: /platri midi [command] [channel] [data1] [data2]
void midi::OSCProxy::send(int command, int channel, int data1, int data2){
	fullPacket->Clear();
	(*fullPacket) << osc::BeginBundleImmediate;
	
	(*fullPacket) << osc::BeginMessage("/platri") << "midi" << command << channel << data1 << data2;
	(*fullPacket) << osc::EndMessage;
	
	(*fullPacket) << osc::EndBundle;
	
	socket->Send(fullPacket->Data(), fullPacket->Size());
}
