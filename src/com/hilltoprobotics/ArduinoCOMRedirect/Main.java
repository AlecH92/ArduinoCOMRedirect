package com.hilltoprobotics.ArduinoCOMRedirect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jssc.SerialPort;
import jssc.SerialPortList;
import jssc.SerialPortException;

public class Main {
	static String s1 = "";
	static String s2 = "";
	static SerialPort serialPort;
	static SerialPort serialPort2;
	public static void main(String[] args) {
		
		System.out.println("Current COM Port Names:");
		String[] portNames = SerialPortList.getPortNames();
		for(int i = 0; i < portNames.length; i++) {
			System.out.println(portNames[i]);
		}
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("1st COM (read from headset): ");
        try {
			s1 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.print("2nd COM (write to bluetooth): ");
        try {
			s2 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        serialPort = new SerialPort(s1);
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        
        serialPort2 = new SerialPort(s2);
        try {
            serialPort2.openPort();//Open serial port
            serialPort2.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        sendData();
	}
	
	static void sendData() {
		while(true) {
			byte[] buffer = null;
			try {
				buffer = serialPort.readBytes(1);
			} catch (SerialPortException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				serialPort2.writeBytes(buffer);
			} catch (SerialPortException e) {
				// 	TODO Auto-generated catch block
				e.printStackTrace();
			}//Write data to port
		}
	}
	
	void finishSerial() {
        try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Close serial port
        try {
			serialPort2.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
