package com.jinju.location.udp;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jinju.location.protocol.DownCommand;
import com.jinju.location.protocol.UpCommand;



public class MyUdpServer {
	public DatagramSocket  server;
	private static MyUdpServer instance;
	private DatagramPacket recvPacket ;
	private  UpCommand command;
	private List<MyUdpListener> listeners;
	private MyUdpServer(){
		
	}
	public static MyUdpServer getInstance(){
		if(instance==null){
			instance=new MyUdpServer();
		}
		return instance;
	}
	public void init(){
		Map<String,DatagramPacket> map=new HashMap<>();
		listeners=new ArrayList<>();
		startServer();
		
	}
	public void addListener(MyUdpListener l){
		listeners.add(l);
	}
	public void removeListener(MyUdpListener l){
		listeners.remove(l);
	}
	public void startServer(){
		try {
			server = new DatagramSocket(5050);
			Thread th=new Thread(()->{
				while(true){
					try{
						byte[] recvBuf = new byte[2048];
						DatagramPacket recvPacket = new DatagramPacket(recvBuf , recvBuf.length);
				        server.receive(recvPacket);
				        this.recvPacket=recvPacket;
				        String recvStr = new String(recvPacket.getData() , 0 , recvPacket.getLength());
				        System.out.println(recvStr);
				        command=JSON.parseObject(recvStr, UpCommand.class);
				        listeners.forEach(l->l.handle(command));
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
				
			});
			th.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean hasClient(){
		return command!=null;
	}
	public void sendToClient(String content){
		if(command==null) {
			System.out.println("no station connect");
			return;
		}
		DownCommand dc=new DownCommand();
		dc.setTarget("node");
		dc.setNodeId(command.getNodeId());
		dc.setContentType("string");
		dc.setContent(content);
		dc.setCommand("10");
		JSONArray ja=new JSONArray();
		ja.add(dc);
		int port = recvPacket.getPort();
        InetAddress addr = recvPacket.getAddress();
        String ss=ja.toJSONString();
        byte[] sd=ss.getBytes();
        DatagramPacket sendPacket  = new DatagramPacket(sd , sd.length , addr , port );
        try {
			server.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 public static void main(String[] args)throws IOException{
	        DatagramSocket  server = new DatagramSocket(5050);
	        byte[] recvBuf = new byte[1024];
	        DatagramPacket recvPacket 
	            = new DatagramPacket(recvBuf , recvBuf.length);
	        server.receive(recvPacket);
	        String recvStr = new String(recvPacket.getData() , 0 , recvPacket.getLength());
	        System.out.println("Hello World!" + recvStr);
	        int port = recvPacket.getPort();
	        InetAddress addr = recvPacket.getAddress();
	        String sendStr = "Hello ! I'm Server";
	        byte[] sendBuf;
	        sendBuf = sendStr.getBytes();
	        DatagramPacket sendPacket 
	            = new DatagramPacket(sendBuf , sendBuf.length , addr , port );
	        server.send(sendPacket);
	        server.close();
	    }
}
