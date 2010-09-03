/*
 *  midi.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-09-03.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _MIDI_H_
#define _MIDI_H_

#include "osc/OscOutboundPacketStream.h"
#include "ip/NetworkingUtils.h"
#include "ip/UdpSocket.h"

// Stolen from http://cupi2.uniandes.edu.co/site/images/recursos/javadoc/j2se/1.5.0/docs/api/javax/sound/midi/ShortMessage.html#ACTIVE_SENSING
// See http://gist.github.com/564012

#define ACTIVE_SENSING         0xFE
#define CHANNEL_PRESSURE       0xD0
#define CONTINUE               0xFB
#define CONTROL_CHANGE         0xB0
#define END_OF_EXCLUSIVE       0xF7
#define MIDI_TIME_CODE         0xF1
#define NOTE_OFF               0x80
#define NOTE_ON                0x90
#define PITCH_BEND             0xE0
#define POLY_PRESSURE          0xA0
#define PROGRAM_CHANGE         0xC0
#define SONG_POSITION_POINTER  0xF2
#define SONG_SELECT            0xF3
#define START                  0xFA
#define STOP                   0xFC
#define SYSTEM_RESET           0xFF
#define TIMING_CLOCK           0xF8
#define TUNE_REQUEST           0xF6


namespace midi {
	void init(const char * address, int port);
	void send(int command, int channel, int data1, int data2);
	void note_on(int data1);
	void note_on(int data1, int data2);
	void note_on(int data1, int data2, int channel);
	
	
	class OSCProxy {
	public:
		OSCProxy();
		OSCProxy(const char * host, int port);
		void initialize(const char *host, int port, int size);
		void send(int command, int channel, int data1, int data2);
		
	protected:
		UdpTransmitSocket * socket;	
		osc::OutboundPacketStream  * fullPacket;
		char * fullBuffer; 
	};
}

#endif
